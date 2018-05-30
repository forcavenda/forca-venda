package br.com.logistica.forcavenda.security;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.logistica.forcavenda.payload.LoginRequest;
import br.com.logistica.forcavenda.payload.RefreshTokenRequest;
import br.com.logistica.forcavenda.payload.RegistrarUsuarioEmpresaRequest;
import br.com.logistica.forcavenda.payload.RegistrarUsuarioRequest;
import br.com.logistica.forcavenda.service.TestMailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = ApplicationTests.class, loader = SpringBootContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Configuration
@ComponentScan({ "br.com.logistica.forcavenda" })
@WebAppConfiguration
@Slf4j
public abstract class ApplicationTests {

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(
    ApplicationTests.class);
  public static final String ADMIN_USERNAME = "alcaphone";
  public static final String ADMIN_PASSWORD = "senha";

  private static final String razaoSocial0 = "Usuario Empresa";
  private static final String email0 = "empresa@empresa.com.br";
  protected static final String cnpj0 = "80.647.634/0001-70";
  private static final String senha0 = "senha_empresa";

  private static final String nome1 = "Usuario da Empresa";
  private static final String email1 = "usuario@empresa.com.br";
  protected static final String nomeUsuario1 = "usuario001";
  private static final String senha1 = "senha_usuario_empresa";

  private static final long DEFAULT_TIMEOUT = -1L;

  protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
    MediaType.APPLICATION_JSON.getSubtype(),
    Charset.forName("utf8"));

  protected MockMvc mockMvc;

  protected String token;
  protected String refreshToken;
  protected String username;

  @SuppressWarnings("rawtypes")
  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  @SuppressWarnings("rawtypes")
  private HttpMessageConverter stringHttpMessageConverter;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Rule
  public TestRule watcher = new TestWatcher() {
    @Override
    protected void starting(Description description) {
      log.info("Starting test: {}", description.getMethodName());
    }

    @Override
    protected void finished(Description description) {
      log.info("Finished test: {}", description.getMethodName());
    }
  };

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {

    mappingJackson2HttpMessageConverter = Arrays.stream(converters)
      .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
      .findAny()
      .get();

    stringHttpMessageConverter = Arrays.stream(converters)
      .filter(hmc -> hmc instanceof StringHttpMessageConverter)
      .findAny()
      .get();

    Assert.assertNotNull("the JSON message converter must not be null",
      mappingJackson2HttpMessageConverter);
  }

  @Before
  public void setup() throws Exception {
    log.info("Executing setup");
    if (mockMvc == null) {
      mockMvc = webAppContextSetup(webApplicationContext).alwaysDo(print())
        .apply(springSecurity()).build();
    }
    loginSysAdmin();

    RegistrarUsuarioEmpresaRequest usrAdmin = new RegistrarUsuarioEmpresaRequest();
    usrAdmin.setRazaoSocial(razaoSocial0);
    usrAdmin.setEmail(email0);
    usrAdmin.setCnpj(cnpj0);
    usrAdmin.setSenha(senha0);
    usrAdmin.setConfirmeSenha(senha0);

    createUserAndLogin(usrAdmin, usrAdmin.getSenha());

    RegistrarUsuarioRequest usr = new RegistrarUsuarioRequest();
    usr.setNome(nome1);
    usr.setEmail(email1);
    usr.setNomeUsuario(nomeUsuario1);
    usr.setSenha(senha1);
    usr.setConfirmeSenha(senha1);

    createUserAndLogin(usr, usr.getSenha());

    logout();
    log.info("Executed setup");
  }

  protected void loginSysAdmin() throws Exception {
    login(ADMIN_USERNAME, ADMIN_PASSWORD);
  }

  protected void loginEmpresa() throws Exception {
    login(cnpj0, senha0);
  }

  protected void loginUsuario() throws Exception {
    login(nomeUsuario1, senha1);
  }

  protected RegistrarUsuarioEmpresaRequest createUserAndLogin(
      RegistrarUsuarioEmpresaRequest usrAdmin, String password)
      throws Exception {
    RegistrarUsuarioEmpresaRequest savedUser = doPost("/api/user", usrAdmin,
      RegistrarUsuarioEmpresaRequest.class);
    logout();
    doGet("/api/noauth/activate?activateToken={activateToken}",
      TestMailService.currentActivateToken)
        .andExpect(status().isSeeOther())
        .andExpect(header().string(HttpHeaders.LOCATION, "/login/createPassword?activateToken="
            + TestMailService.currentActivateToken));

    JsonNode activateRequest = new ObjectMapper().createObjectNode()
      .put("activateToken", TestMailService.currentActivateToken)
      .put("password", password);

    JsonNode tokenInfo = readResponse(doPost("/api/noauth/activate", activateRequest).andExpect(
      status().isOk()), JsonNode.class);

    validateAndSetJwtToken(tokenInfo, usrAdmin.getEmail());
    return savedUser;
  }

  protected RegistrarUsuarioRequest createUserAndLogin(
      RegistrarUsuarioRequest usrAdmin, String password)
      throws Exception {
    RegistrarUsuarioRequest savedUser = doPost("/api/user", usrAdmin,
      RegistrarUsuarioRequest.class);
    logout();
    doGet("/api/noauth/activate?activateToken={activateToken}",
      TestMailService.currentActivateToken)
        .andExpect(status().isSeeOther())
        .andExpect(header().string(HttpHeaders.LOCATION, "/login/createPassword?activateToken="
            + TestMailService.currentActivateToken));

    JsonNode activateRequest = new ObjectMapper().createObjectNode()
      .put("activateToken", TestMailService.currentActivateToken)
      .put("password", password);

    JsonNode tokenInfo = readResponse(doPost("/api/noauth/activate", activateRequest).andExpect(
      status().isOk()), JsonNode.class);

    validateAndSetJwtToken(tokenInfo, usrAdmin.getEmail());
    return savedUser;
  }

  protected void login(String username, String password) throws Exception {
    token = null;
    refreshToken = null;
    this.username = null;
    JsonNode tokenInfo = readResponse(doPost("/api/auth/login", new LoginRequest(username,
      password)).andExpect(status().isOk()), JsonNode.class);
    validateAndSetJwtToken(tokenInfo, username);
  }

  protected void refreshToken() throws Exception {
    token = null;
    JsonNode tokenInfo = readResponse(doPost("/api/auth/token", new RefreshTokenRequest(
      refreshToken)).andExpect(status().isOk()), JsonNode.class);
    validateAndSetJwtToken(tokenInfo, username);
  }

  protected void validateAndSetJwtToken(JsonNode tokenInfo, String username) {
    Assert.assertNotNull(tokenInfo);
    Assert.assertTrue(tokenInfo.has("token"));
    Assert.assertTrue(tokenInfo.has("refreshToken"));
    String token = tokenInfo.get("token").asText();
    String refreshToken = tokenInfo.get("refreshToken").asText();
    validateJwtToken(token, username);
    validateJwtToken(refreshToken, username);
    this.token = token;
    this.refreshToken = refreshToken;
    this.username = username;
  }

  protected void validateJwtToken(String token, String username) {
    Assert.assertNotNull(token);
    Assert.assertFalse(token.isEmpty());
    int i = token.lastIndexOf('.');
    Assert.assertTrue(i > 0);
    String withoutSignature = token.substring(0, i + 1);
    Jwt<Header, Claims> jwsClaims = Jwts.parser().parseClaimsJwt(withoutSignature);
    Claims claims = jwsClaims.getBody();
    String subject = claims.getSubject();
    Assert.assertEquals(username, subject);
  }

  protected void logout() throws Exception {
    token = null;
    refreshToken = null;
    username = null;
  }

  protected void setJwtToken(MockHttpServletRequestBuilder request) {
    if (token != null) {
      request.header(WebSecurityConfiguration.JWT_TOKEN_HEADER_PARAM, "Bearer " + token);
    }
  }

  protected ResultActions doGet(String urlTemplate, Object... urlVariables) throws Exception {
    MockHttpServletRequestBuilder getRequest = get(urlTemplate, urlVariables);
    setJwtToken(getRequest);
    return mockMvc.perform(getRequest);
  }

  protected <T> T doGet(String urlTemplate, Class<T> responseClass, Object... urlVariables)
      throws Exception {
    return readResponse(doGet(urlTemplate, urlVariables).andExpect(status().isOk()), responseClass);
  }

  protected <T> T doGetAsync(String urlTemplate, Class<T> responseClass, Object... urlVariables)
      throws Exception {
    return readResponse(doGetAsync(urlTemplate, urlVariables).andExpect(status().isOk()),
      responseClass);
  }

  protected ResultActions doGetAsync(String urlTemplate, Object... urlVariables) throws Exception {
    MockHttpServletRequestBuilder getRequest;
    getRequest = get(urlTemplate, urlVariables);
    setJwtToken(getRequest);
    return mockMvc.perform(asyncDispatch(mockMvc.perform(getRequest).andExpect(request()
      .asyncStarted()).andReturn()));
  }

  protected <T> T doGetTyped(String urlTemplate, TypeReference<T> responseType,
      Object... urlVariables) throws Exception {
    return readResponse(doGet(urlTemplate, urlVariables).andExpect(status().isOk()), responseType);
  }

  protected <T> T doPost(String urlTemplate, Class<T> responseClass, String... params)
      throws Exception {
    return readResponse(doPost(urlTemplate, params).andExpect(status().isOk()), responseClass);
  }

  protected <T> T doPost(String urlTemplate, T content, Class<T> responseClass,
      ResultMatcher resultMatcher, String... params) throws Exception {
    return readResponse(doPost(urlTemplate, content, params).andExpect(resultMatcher),
      responseClass);
  }

  protected <T> T doPost(String urlTemplate, T content, Class<T> responseClass, String... params)
      throws Exception {
    return readResponse(doPost(urlTemplate, content, params).andExpect(status().isOk()),
      responseClass);
  }

  protected <T> T doPostAsync(String urlTemplate, T content, Class<T> responseClass,
      ResultMatcher resultMatcher, String... params) throws Exception {
    return readResponse(doPostAsync(urlTemplate, content, DEFAULT_TIMEOUT, params).andExpect(
      resultMatcher), responseClass);
  }

  protected <T> T doPostAsync(String urlTemplate, T content, Class<T> responseClass,
      ResultMatcher resultMatcher, Long timeout, String... params) throws Exception {
    return readResponse(doPostAsync(urlTemplate, content, timeout, params).andExpect(resultMatcher),
      responseClass);
  }

  protected <T> T doDelete(String urlTemplate, Class<T> responseClass, String... params)
      throws Exception {
    return readResponse(doDelete(urlTemplate, params).andExpect(status().isOk()), responseClass);
  }

  protected ResultActions doPost(String urlTemplate, String... params) throws Exception {
    MockHttpServletRequestBuilder postRequest = post(urlTemplate);
    setJwtToken(postRequest);
    populateParams(postRequest, params);
    return mockMvc.perform(postRequest);
  }

  protected <T> ResultActions doPost(String urlTemplate, T content, String... params)
      throws Exception {
    MockHttpServletRequestBuilder postRequest = post(urlTemplate);
    setJwtToken(postRequest);
    String json = json(content);
    postRequest.contentType(contentType).content(json);
    return mockMvc.perform(postRequest);
  }

  protected <T> ResultActions doPostAsync(String urlTemplate, T content, Long timeout,
      String... params) throws Exception {
    MockHttpServletRequestBuilder postRequest = post(urlTemplate);
    setJwtToken(postRequest);
    String json = json(content);
    postRequest.contentType(contentType).content(json);
    MvcResult result = mockMvc.perform(postRequest).andReturn();
    result.getAsyncResult(timeout);
    return mockMvc.perform(asyncDispatch(result));
  }

  protected ResultActions doDelete(String urlTemplate, String... params) throws Exception {
    MockHttpServletRequestBuilder deleteRequest = delete(urlTemplate);
    setJwtToken(deleteRequest);
    populateParams(deleteRequest, params);
    return mockMvc.perform(deleteRequest);
  }

  protected void populateParams(MockHttpServletRequestBuilder request, String... params) {
    if (params != null && params.length > 0) {
      Assert.assertEquals(0, params.length % 2);
      MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
      for (int i = 0; i < params.length; i += 2) {
        paramsMap.add(params[i], params[i + 1]);
      }
      request.params(paramsMap);
    }
  }

  @SuppressWarnings("unchecked")
  protected String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();

    HttpMessageConverter converter = o instanceof String
        ? stringHttpMessageConverter
        : mappingJackson2HttpMessageConverter;
    converter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }

  @SuppressWarnings("unchecked")
  protected <T> T readResponse(ResultActions result, Class<T> responseClass) throws Exception {
    byte[] content = result.andReturn().getResponse().getContentAsByteArray();
    MockHttpInputMessage mockHttpInputMessage = new MockHttpInputMessage(content);
    HttpMessageConverter converter = responseClass.equals(String.class)
        ? stringHttpMessageConverter
        : mappingJackson2HttpMessageConverter;
    return (T) converter.read(responseClass, mockHttpInputMessage);
  }

  protected <T> T readResponse(ResultActions result, TypeReference<T> type) throws Exception {
    byte[] content = result.andReturn().getResponse().getContentAsByteArray();
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readerFor(type).readValue(content);
  }

  protected static <T> ResultMatcher statusReason(Matcher<T> matcher) {
    return jsonPath("$.message", matcher);
  }

  /*
   * @Before
   * public void setUp() throws Exception {
   * mockMvc = MockMvcBuilders
   * .webAppContextSetup(webApplicationContext)
   * .apply(springSecurity())
   * .alwaysDo(print())
   * .build();
   * }
   * @Test
   * public void loginAvailableForAll() throws Exception {
   * mockMvc
   * .perform(get("/api/auth").accept(MediaType.APPLICATION_JSON))
   * .andExpect(status().isOk());
   * }
   * @Test
   * public void adminCanLog() throws Exception {
   * mockMvc
   * .perform(formLogin("/api/auth")
   * .acceptMediaType(MediaType.APPLICATION_JSON)
   * .user("nomeUsuario", ApplicationTests.ADMIN_USERNAME)
   * .password("senha", ApplicationTests.ADMIN_PASSWORD))
   * .andExpect(status().isFound())
   * .andExpect(redirectedUrl("/api/dashboard"))
   * .andExpect(authenticated().withUsername(ApplicationTests.ADMIN_USERNAME));
   * mockMvc
   * .perform(logout())
   * .andExpect(status().isFound())
   * .andExpect(redirectedUrl("/api/index"));
   * }
   * @Test
   * public void invalidLoginDenied() throws Exception {
   * mockMvc
   * .perform(formLogin("/api/auth")
   * .acceptMediaType(MediaType.APPLICATION_JSON)
   * .user("nomeUsuario", "")
   * .password("senha", ApplicationTests.ADMIN_PASSWORD))
   * .andExpect(status().isFound())
   * .andExpect(redirectedUrl("/api/auth?error=true"))
   * .andExpect(unauthenticated());
   * mockMvc
   * .perform(get("/api/auth?error=true"))
   * .andExpect(content().string(containsString("Usuário e senha invalido")));
   * }
   */

}

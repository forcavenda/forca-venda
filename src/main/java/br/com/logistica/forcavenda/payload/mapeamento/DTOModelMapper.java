package br.com.logistica.forcavenda.payload.mapeamento;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collections;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DTOModelMapper extends RequestResponseBodyMethodProcessor {

  private static final ModelMapper modelMapper = new ModelMapper();
  private MongoTemplate mongoTemplate;

  public DTOModelMapper(ObjectMapper objectMapper, MongoTemplate mongoTemplate) {
    super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(DTO.class);
  }

  @Override
  protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
    binder.validate();
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Object dto = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    Object id = getEntityId(dto);
    if (id == null) {
      return modelMapper.map(dto, parameter.getParameterType());
    } else {
      Object persistedObject = mongoTemplate.findById(id, parameter.getParameterType());
      modelMapper.map(dto, persistedObject);
      return persistedObject;
    }
  }

  @Override
  protected Object readWithMessageConverters(HttpInputMessage inputMessage,
      MethodParameter parameter, Type targetType) throws IOException,
      HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
    for (Annotation ann : parameter.getParameterAnnotations()) {
      DTO dtoType = AnnotationUtils.getAnnotation(ann, DTO.class);
      if (dtoType != null) {
        return super.readWithMessageConverters(inputMessage, parameter, dtoType.value());
      }
    }
    throw new RuntimeException();
  }

  private Object getEntityId(@NotNull Object dto) {
    for (Field field : dto.getClass().getDeclaredFields()) {
      if (field.getAnnotation(Id.class) != null) {
        try {
          field.setAccessible(true);
          return field.get(dto);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }
    return null;
  }
}

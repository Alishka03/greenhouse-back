package kz.iitu.smartgreenhouse.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.iitu.smartgreenhouse.model.LoggerELK;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoData;
import kz.iitu.smartgreenhouse.model.wrapper.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Log4j2
public class LoggingAspect {
    private final ObjectMapper mapper;

    @Around("kz.iitu.smartgreenhouse.aspect.AopExpressions.pointcutLogging()")
    public Object logTestEndpoint(ProceedingJoinPoint joinPoint) {
        LoggerELK loggerELK = new LoggerELK();
        Map<String, Object> parameters = getParameters(joinPoint);
        loggerELK.setDateTime(LocalDateTime.now());
        try {
            Object response = joinPoint.proceed(joinPoint.getArgs());
//            CustomResponse<?> ifSuccessFalse = (CustomResponse<?>) response;
//            if (!ifSuccessFalse.getSuccess()) {
//                throw new Exception("success = false");
//            }
            if (joinPoint.getSignature().getName().equals("insertData")) {
                ArduinoData temp = (ArduinoData) parameters.get("data");
                loggerELK.setArduinoId(temp.getId());
                loggerELK.setHumidityAir(temp.getHumidityAir());
                loggerELK.setHumidityGround(temp.getHumidityGround());
                loggerELK.setCo2(temp.getCo2());
                loggerELK.setLight(temp.getLight());
                loggerELK.setTemperature(temp.getTemperature());
            }
            if (joinPoint.getSignature().getName().equals("insertDataTest")){
                ArduinoData temp = (ArduinoData) parameters.get("data");
                loggerELK.setArduinoId(temp.getId());
                loggerELK.setHumidityAir(temp.getHumidityAir());
                loggerELK.setHumidityGround(temp.getHumidityGround());
                loggerELK.setCo2(temp.getCo2());
                loggerELK.setLight(temp.getLight());
                loggerELK.setTemperature(temp.getTemperature());
            }
            log.debug("{} ", mapper.writeValueAsString(loggerELK));
            System.out.println("log destinated : " + mapper.writeValueAsString(loggerELK));
            return response;
        } catch (JsonProcessingException e) {
            log.error("Error while converting", e);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        HashMap<String, Object> map = new HashMap<>();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }

        return map;
    }
}

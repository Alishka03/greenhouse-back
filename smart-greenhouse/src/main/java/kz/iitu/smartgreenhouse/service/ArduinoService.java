package kz.iitu.smartgreenhouse.service;

import kz.iitu.smartgreenhouse.feign.AuthServiceFeign;
import kz.iitu.smartgreenhouse.mapper.ArduinoMapper;
import kz.iitu.smartgreenhouse.model.Arduino;
import kz.iitu.smartgreenhouse.model.Notifications;
import kz.iitu.smartgreenhouse.model.Plant;
import kz.iitu.smartgreenhouse.model.User;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoData;
import kz.iitu.smartgreenhouse.model.dto.ArduinoDto;
import kz.iitu.smartgreenhouse.model.criteria.ArduinoCriteria;
import kz.iitu.smartgreenhouse.model.dto.InsertResponseDto;
import kz.iitu.smartgreenhouse.model.dto.PageResponse;
import kz.iitu.smartgreenhouse.model.dto.WarningDto;
import kz.iitu.smartgreenhouse.repository.ArduinoRepository;
import kz.iitu.smartgreenhouse.repository.UserRepository;
import kz.iitu.smartgreenhouse.web.rest.errors.BadRequestError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArduinoService {
    private final ArduinoRepository arduinoRepository;

    private final ArduinoMapper arduinoMapper;

    private final AuthServiceFeign authServiceFeign;

    private final UserRepository userRepository;

    private final NotificationService notificationService;

    private final NotificationsEntityService notificationsEntityService;

    public ArduinoService(ArduinoRepository arduinoRepository, ArduinoMapper arduinoMapper, AuthServiceFeign authServiceFeign, UserRepository userRepository, NotificationService notificationService, NotificationsEntityService notificationsEntityService) {
        this.arduinoRepository = arduinoRepository;
        this.arduinoMapper = arduinoMapper;
        this.authServiceFeign = authServiceFeign;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.notificationsEntityService = notificationsEntityService;
    }

    public ArduinoDto save(ArduinoDto arduinoDto) {
        Arduino arduino = arduinoMapper.toEntity(arduinoDto);
        arduino = arduinoRepository.save(arduino);
        return arduinoMapper.toDto(arduino);
    }

    public Optional<ArduinoDto> partialUpdate(ArduinoDto arduinoDto) {
        return arduinoRepository
                .findById(arduinoDto.getId())
                .map(existingArduino -> {
                    arduinoMapper.partialUpdate(existingArduino, arduinoDto);
                    return existingArduino;
                })
                .map(arduinoRepository::save)
                .map(arduinoMapper::toDto);
    }

    public PageResponse<ArduinoDto> findAllPageable(ArduinoCriteria arduinoCriteria) {
        PageResponse<ArduinoDto> response = new PageResponse<>();
        Page<Arduino> arduinoPage = arduinoRepository.findAll(
                PageRequest.of(
                        arduinoCriteria.getPage() > 0 ? arduinoCriteria.getPage() - 1 : 0,
                        arduinoCriteria.getSize()
                )
        );

        List<ArduinoDto> arduinoDTOs = arduinoPage.getContent().stream()
                .map(arduinoMapper::toDto)
                .collect(Collectors.toList());

        response.setTotalCount(arduinoPage.getTotalElements());
        response.setTotalPages(arduinoPage.getTotalPages());
        response.setItems(arduinoDTOs);

        return response;
    }
    public Optional<ArduinoDto> findById(Long id) {
        return arduinoRepository.findById(id).map(arduinoMapper::toDto);
    }


    public void deleteArduino(Long id) {

        arduinoRepository.deleteById(id);
    }


    @Transactional
    public InsertResponseDto insertingData(ArduinoData data) {
        Optional<Arduino> optionalArduino = arduinoRepository.findById(data.getId());
        if (optionalArduino.isEmpty()) {
            throw new BadRequestError("Arduino not found with ID: " + data.getId());
        }
        Arduino arduino = optionalArduino.get();
        Plant currentPlant = arduino.getPlant();

        boolean optimalTemperature = isWithinRange(data.getTemperature(), currentPlant.getMinimumTemperature(), currentPlant.getMaximumTemperature());
        boolean optimalHumidityAir = isWithinRange(data.getHumidityAir(), currentPlant.getMinimumHumidityAir(), currentPlant.getMaximumHumidityAir());
        boolean optimalHumidityGround = isWithinRange(data.getHumidityGround(), currentPlant.getMinimumHumidityGround(), currentPlant.getMaximumHumidityGround());
        boolean optimalLight = isWithinRange(data.getLight(), currentPlant.getMinimumLight(), currentPlant.getMaximumLight());
        boolean optimalCarbonDioxide = isWithinRange(data.getCo2(), currentPlant.getMinimumCarbonDioxide(), currentPlant.getMaximumCarbonDioxide());

        if (data.getCo2() != null) {
            arduino.setCarbonDioxide(data.getCo2());
        }
        if (data.getHumidityAir() != null) {
            arduino.setHumidityAir(data.getHumidityAir());
        }
        if (data.getHumidityGround() != null) {
            arduino.setHumidityGround(data.getHumidityGround());
        }
        if (data.getTemperature() != null) {
            arduino.setTemperature(data.getTemperature());
        }
        if (data.getLight() != null) {
            arduino.setLight(data.getLight());
        }
        Arduino savedArduino = arduinoRepository.save(arduino);
        WarningDto warningDto = WarningDto.builder()
                .optimalTemperature(optimalTemperature)
                .optimalHumidityAir(optimalHumidityAir)
                .optimalHumidityGround(optimalHumidityGround)
                .optimalLight(optimalLight)
                .optimalCarbonDioxide(optimalCarbonDioxide)
                .build();
        return new InsertResponseDto(savedArduino, warningDto);
    }

    private boolean isWithinRange(Float value, Float min, Float max) {
        return value != null && value >= min && value <= max;
    }


    @Transactional
    public WarningDto insertDataTest(ArduinoData data) {
        Optional<Arduino> optionalArduino = arduinoRepository.findById(data.getId());
        if (optionalArduino.isEmpty()) {
            throw new BadRequestError("Arduino not found with ID: " + data.getId());
        }
        Arduino arduino = optionalArduino.get();
        Plant currentPlant = arduino.getPlant();
        Optional<User> host = userRepository.findByArduinoId(data.getId());
        boolean optimalTemperature = isWithinRange(data.getTemperature(), currentPlant.getMinimumTemperature(), currentPlant.getMaximumTemperature());
        boolean optimalHumidityAir = isWithinRange(data.getHumidityAir(), currentPlant.getMinimumHumidityAir(), currentPlant.getMaximumHumidityAir());
        boolean optimalHumidityGround = isWithinRange(data.getHumidityGround(), currentPlant.getMinimumHumidityGround(), currentPlant.getMaximumHumidityGround());
        boolean optimalLight = isWithinRange(data.getLight(), currentPlant.getMinimumLight(), currentPlant.getMaximumLight());
        boolean optimalCarbonDioxide = isWithinRange(data.getCo2(), currentPlant.getMinimumCarbonDioxide(), currentPlant.getMaximumCarbonDioxide());
        if (data.getCo2() != null) {
            arduino.setCarbonDioxide(data.getCo2());
        }
        if (data.getHumidityAir() != null) {
            arduino.setHumidityAir(data.getHumidityAir());
        }
        if (data.getTemperature() != null) {
            arduino.setTemperature(data.getTemperature());
        }
        if (data.getLight() != null) {
            arduino.setLight(data.getLight());
        }
        if (data.getHumidityGround() != null) {
            arduino.setHumidityGround(data.getHumidityGround());
        }
        arduinoRepository.save(arduino);
        if (!optimalTemperature || !optimalHumidityAir || !optimalHumidityGround || !optimalLight || !optimalCarbonDioxide) {
            if (host.isPresent()) {
                WarningDto warningDto = WarningDto.builder()
                        .optimalTemperature(optimalTemperature)
                        .optimalHumidityAir(optimalHumidityAir)
                        .optimalHumidityGround(optimalHumidityGround)
                        .optimalLight(optimalLight)
                        .optimalCarbonDioxide(optimalCarbonDioxide)
                        .build();

                // Retrieve notifications for the Arduino
                Optional<Notifications> notificationsOptional = notificationsEntityService.findByArduinoId(arduino.getId());
                if (notificationsOptional.isPresent()) {
                    Notifications notifications = notificationsOptional.get();

                    // Compare the warning with the notifications
                    if (!compare(warningDto, notifications)) {
                        // Update notifications with warning values
                        notifications.setOptimalCarbonDioxide(warningDto.getOptimalCarbonDioxide());
                        notifications.setOptimalLight(warningDto.getOptimalLight());
                        notifications.setOptimalHumidityAir(warningDto.getOptimalHumidityAir());
                        notifications.setOptimalHumidityGround(warningDto.getOptimalHumidityGround());
                        notifications.setOptimalTemperature(warningDto.getOptimalTemperature());
                        notificationsEntityService.save(notifications);
                        log.info("NOTIFICATIONS: ",notifications.toString());
                        // Send notification
                        notificationService.sendNotification(host.get().getDeviceId(), warningDto, arduino);
                    }
                } else {
                    // If no notifications found, create new ones with warning values
                    Notifications newEntity = Notifications.builder()
                            .optimalCarbonDioxide(warningDto.getOptimalCarbonDioxide())
                            .optimalLight(warningDto.getOptimalLight())
                            .optimalHumidityAir(warningDto.getOptimalHumidityAir())
                            .optimalHumidityGround(warningDto.getOptimalHumidityGround())
                            .optimalTemperature(warningDto.getOptimalTemperature())
                            .arduino(arduino)
                            .build();
                    log.info("NOTIFICATIONS: ",newEntity.toString());
                    notificationsEntityService.save(newEntity);
                    // Send notification
                    notificationService.sendNotification(host.get().getDeviceId(), warningDto, arduino);
                }
                return warningDto;
            }
        }
        // If all sensor data are within optimal range, return default warning
        return WarningDto.builder()
                .optimalTemperature(true)
                .optimalHumidityAir(true)
                .optimalHumidityGround(true)
                .optimalLight(true)
                .optimalCarbonDioxide(true)
                .build();
    }

    private boolean compare(WarningDto warning, Notifications notification) {
        return Objects.equals(warning.getOptimalLight(), notification.getOptimalLight()) &&
                Objects.equals(warning.getOptimalCarbonDioxide(), notification.getOptimalCarbonDioxide()) &&
                Objects.equals(warning.getOptimalHumidityAir(), notification.getOptimalHumidityAir()) &&
                Objects.equals(warning.getOptimalHumidityGround(), notification.getOptimalHumidityGround()) &&
                Objects.equals(warning.getOptimalTemperature(), notification.getOptimalTemperature());
    }


}

package com.example.cabmanagement.models.dao;

import com.example.cabmanagement.models.common.TripStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table
@Entity
@Data
@NoArgsConstructor
public class TripEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tripId;
    private Long cabId;
    private String cityCode;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
    private LocalDateTime tripStartTime;
    private LocalDateTime tripEndTime;

    public static final class TripEntityBuilder {
        private Long cabId;
        private String cityCode;
        private BigDecimal price;
        private TripStatus tripStatus;
        private LocalDateTime tripStartTime;
        private LocalDateTime tripEndTime;

        private TripEntityBuilder() {
        }

        public static TripEntityBuilder aTripEntity() {
            return new TripEntityBuilder();
        }

        public TripEntityBuilder withCabId(Long cabId) {
            this.cabId = cabId;
            return this;
        }

        public TripEntityBuilder withCityCode(String cityCode) {
            this.cityCode = cityCode;
            return this;
        }

        public TripEntityBuilder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public TripEntityBuilder withTripStatus(TripStatus tripStatus) {
            this.tripStatus = tripStatus;
            return this;
        }

        public TripEntityBuilder withTripStartTime(LocalDateTime tripStartTime) {
            this.tripStartTime = tripStartTime;
            return this;
        }

        public TripEntityBuilder withTripEndTime(LocalDateTime tripEndTime) {
            this.tripEndTime = tripEndTime;
            return this;
        }

        public TripEntity build() {
            TripEntity tripEntity = new TripEntity();
            tripEntity.setCabId(cabId);
            tripEntity.setCityCode(cityCode);
            tripEntity.setPrice(price);
            tripEntity.setTripStatus(tripStatus);
            tripEntity.setTripStartTime(tripStartTime);
            tripEntity.setTripEndTime(tripEndTime);
            return tripEntity;
        }
    }
}

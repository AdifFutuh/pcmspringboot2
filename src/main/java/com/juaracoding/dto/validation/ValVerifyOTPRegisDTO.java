package com.juaracoding.dto.validation;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ValVerifyOTPRegisDTO {

    @Pattern(regexp = "^([0-9]{6})$",
            message = "Format OTP Wajib 6 Angka")
    private String otp;

    @NotNull
    @NotBlank
    @NotEmpty
    private String email;

    public @Pattern(regexp = "^([0-9]{6})$",
            message = "Format OTP Wajib 6 Angka") String getOtp() {
        return otp;
    }

    public void setOtp(@Pattern(regexp = "^([0-9]{6})$",
            message = "Format OTP Wajib 6 Angka") String otp) {
        this.otp = otp;
    }

    public @NotNull @NotBlank @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @NotBlank @NotEmpty String email) {
        this.email = email;
    }
}

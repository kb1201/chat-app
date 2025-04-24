package hr.fer.ppks.chat.exception;

import java.util.List;

public record ValidationErrorResponse(List<FieldValidationError> errors) {}


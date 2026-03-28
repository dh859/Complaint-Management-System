package com.cms.cmsapp.common.exceptions;

public class InvalidOperationException extends RuntimeException {

    // Marking already-paid invoice as paid again
    // Deleting transaction linked to finalized invoice

    public InvalidOperationException(String message) {
        super(message);
    }
}
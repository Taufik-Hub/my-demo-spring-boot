package com.file.upload.download.dto;

public class DocumentStorageException extends RuntimeException {
	private static final long serialVersionUID = -639169778750989684L;

	public DocumentStorageException(String message) {
        super(message);
    }

    public DocumentStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
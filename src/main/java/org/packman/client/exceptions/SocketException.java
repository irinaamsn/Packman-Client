package org.packman.client.exceptions;

import org.packman.client.exceptions.base.GlobalPackmanException;

public class SocketException extends GlobalPackmanException {
    public SocketException(int status, String errorMessage, long timestamp) {
        super(status, errorMessage, timestamp);
    }
}

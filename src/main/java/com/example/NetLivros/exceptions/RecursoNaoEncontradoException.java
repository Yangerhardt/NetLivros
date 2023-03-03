package com.example.NetLivros.exceptions;

import java.util.function.Supplier;

public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String msg) {
        super(msg);
    }
}

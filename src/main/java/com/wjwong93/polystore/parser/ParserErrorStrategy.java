package com.wjwong93.polystore.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ParserErrorStrategy extends DefaultErrorStrategy {
    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        // Do not attempt to recover, just throw an exception
        throw new ParseCancellationException("Failed to parse input.", e);
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        // Do not attempt to recover, just throw an exception
        InputMismatchException e = new InputMismatchException(recognizer);
        throw new ParseCancellationException("Failed to parse input. Reason: " + e.getMessage(), e);
    }
}

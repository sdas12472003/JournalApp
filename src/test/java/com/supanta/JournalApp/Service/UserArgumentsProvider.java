package com.supanta.JournalApp.Service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.supanta.JournalApp.Entity.User;

public class UserArgumentsProvider implements ArgumentsProvider{

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
       return Stream.of(
        Arguments.of(User.builder().userName("ram").password("ram123").build()),
        Arguments.of(User.builder().userName("shyam").password("shyam123").build())
       );
    }

}

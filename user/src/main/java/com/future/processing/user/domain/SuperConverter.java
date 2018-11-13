package com.future.processing.user.domain;

import javax.validation.constraints.NotNull;

import java.util.Collection;

interface SuperConverter<T, R, U> {

    R convert(@NotNull T t);

    Collection<R> convert(@NotNull Collection<T> t);

    R update(@NotNull T updater, @NotNull R result);

    R updateExisting(@NotNull U upadter, @NotNull R result);
}

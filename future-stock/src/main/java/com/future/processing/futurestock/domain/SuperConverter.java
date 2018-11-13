package com.future.processing.futurestock.domain;

import javax.validation.constraints.NotNull;
import java.util.Collection;

interface SuperConverter<T, R> {

    R convert(@NotNull T t);

    Collection<R> convert(@NotNull Collection<T> t);

    R update(@NotNull T updater, @NotNull R result);

    R create(@NotNull T updater, @NotNull R result);
}

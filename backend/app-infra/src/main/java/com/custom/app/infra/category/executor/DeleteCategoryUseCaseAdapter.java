package com.custom.app.infra.category.executor;

import com.custom.app.core.usecase.category.DeleteCategoryUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCategoryUseCaseAdapter implements DeleteCategoryUseCase {

    @Override
    public void execute(UUID userId) {

    }
}
package io.github.kamarias.dbf.system.context;

import io.github.kamarias.dto.ContextRequest;

public interface Context<Q, R> {


    Q getRequest();


    R getResponse();

}

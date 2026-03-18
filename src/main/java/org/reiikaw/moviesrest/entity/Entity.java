package org.reiikaw.moviesrest.entity;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable {

    ID getId();
}

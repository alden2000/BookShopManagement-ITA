package com.finalTest.library.states.entity;

import com.finalTest.library.states.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}


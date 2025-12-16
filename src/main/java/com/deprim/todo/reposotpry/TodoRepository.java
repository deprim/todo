package com.deprim.todo.reposotpry;

import com.deprim.todo.model.Todo;
import com.deprim.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {


    List<Todo> findByCompletedTrue();

    List<Todo> findByUser(User user);

    List<Todo> findByUserAndCompletedIsTrue(User user);

    List<Todo> findByUserAndCompletedIsFalse(User user);


}




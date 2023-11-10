package com.korea.test.postbook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostbookRepository extends JpaRepository<Postbook,Long> {
    List<Postbook> findByParentId(Postbook parent);
}

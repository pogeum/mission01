package com.korea.test.postbook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostbookRepository extends JpaRepository<Postbook,Long> {
}

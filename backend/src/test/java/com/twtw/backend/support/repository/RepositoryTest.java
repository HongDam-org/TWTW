package com.twtw.backend.support.repository;

import com.twtw.backend.support.exclude.ExcludeTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@EnableDataJpa
@SpringBootTest
@ExtendWith(SpringExtension.class)
public abstract class RepositoryTest extends ExcludeTest {}

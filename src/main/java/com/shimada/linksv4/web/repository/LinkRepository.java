package com.shimada.linksv4.web.repository;

import com.shimada.linksv4.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}

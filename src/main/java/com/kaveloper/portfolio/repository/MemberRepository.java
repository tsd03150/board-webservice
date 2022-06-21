package com.kaveloper.portfolio.repository;

import com.kaveloper.portfolio.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndWebCode(String email, String webCode);
}

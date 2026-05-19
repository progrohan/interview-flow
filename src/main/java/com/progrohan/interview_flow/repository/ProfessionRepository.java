package com.progrohan.interview_flow.repository;

import com.progrohan.interview_flow.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfessionRepository extends JpaRepository<Profession, Long> {

}

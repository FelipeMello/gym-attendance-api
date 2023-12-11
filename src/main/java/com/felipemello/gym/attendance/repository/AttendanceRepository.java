package com.felipemello.gym.attendance.repository;

import com.felipemello.gym.attendance.entity.Attendance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

  List<Attendance> findByMemberIdOrderByDateAsc(Long memberId);
}

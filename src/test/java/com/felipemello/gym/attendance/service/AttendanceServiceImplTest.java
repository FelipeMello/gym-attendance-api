package com.felipemello.gym.attendance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.felipemello.gym.attendance.entity.Attendance;
import com.felipemello.gym.attendance.entity.Member;
import com.felipemello.gym.attendance.exceptions.UserNotFoundException;
import com.felipemello.gym.attendance.repository.AttendanceRepository;
import com.felipemello.gym.attendance.repository.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class AttendanceServiceImplTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private AttendanceRepository attendanceRepository;

  @InjectMocks
  private AttendanceServiceImpl attendanceService;

  @Test
  void testAddAttendance() {
    Long memberId = 1L;
    LocalDate date = LocalDate.now();

    Member member = new Member();
    member.setId(memberId);

    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

    attendanceService.addAttendance(memberId, date);

    verify(attendanceRepository, times(1)).save(Mockito.any(Attendance.class));
  }

  @Test
  void testAddAttendanceUserNotFound() {
    Long memberId = 1L;
    LocalDate date = LocalDate.now();

    when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class,
        () -> attendanceService.addAttendance(memberId, date));

    verify(attendanceRepository, Mockito.never()).save(Mockito.any(Attendance.class));
  }

  @Test
  void testCalculateAttendanceStreak() {
    Long memberId = 1L;

    List<Attendance> attendances = createAttendances(memberId);

    when(attendanceRepository.findByMemberIdOrderByDateDesc(memberId)).thenReturn(attendances);

    int streak = attendanceService.calculateAttendanceStreak(memberId);

    assertEquals(5, streak);
  }

  @Test
  void testCalculateAttendanceStreakDemoFailed() {
    /*
            {
            "id": 1,
            "date": "2023-12-04"
        },
        {
            "id": 2,
            "date": "2023-11-28"
        },
        {
            "id": 3,
            "date": "2023-11-24"
        },
        {
            "id": 4,
            "date": "2023-12-12"
        }
     */
    Long memberId = 1L;

    List<Attendance> attendances = new ArrayList<>();
    attendances.add(createAttendance(memberId, LocalDate.of(2023, 12, 4)));
    attendances.add(createAttendance(memberId, LocalDate.of(2023, 11, 28)));
    attendances.add(createAttendance(memberId, LocalDate.of(2023, 11, 24)));
    attendances.add(createAttendance(memberId, LocalDate.of(2023, 12, 12)));
    Collections.sort(attendances, Comparator.comparing(Attendance::getDate).reversed());
    when(attendanceRepository.findByMemberIdOrderByDateDesc(memberId)).thenReturn(attendances);

    int streak = attendanceService.calculateAttendanceStreak(memberId);

    assertEquals(4, streak);
  }



  private List<Attendance> createAttendances(Long memberId) {
    List<Attendance> attendances = new ArrayList<>();
    attendances.add(createAttendance(memberId, LocalDate.now().minusDays(12)));
    attendances.add(createAttendance(memberId, LocalDate.now().minusDays(7)));
    attendances.add(createAttendance(memberId, LocalDate.now().minusDays(6)));
    attendances.add(createAttendance(memberId, LocalDate.now().minusDays(5)));
    attendances.add(createAttendance(memberId, LocalDate.now().minusDays(3)));
    attendances.add(createAttendance(memberId, LocalDate.now().minusDays(2)));
    attendances.add(createAttendance(memberId, LocalDate.now().minusDays(1)));
    attendances.add(createAttendance(memberId, LocalDate.now()));
    return attendances;
  }


  private Attendance createAttendance(Long memberId, LocalDate date) {
    Attendance attendance = new Attendance();
    attendance.setMember(
        new Member(memberId, "Test", "test@example.com", "password", new ArrayList<>()));
    attendance.setDate(date);
    return attendance;
  }

}
package com.felipemello.gym.attendance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.felipemello.gym.attendance.entity.Attendance;
import com.felipemello.gym.attendance.entity.User;
import com.felipemello.gym.attendance.exceptions.UserNotFoundException;
import com.felipemello.gym.attendance.repository.AttendanceRepository;
import com.felipemello.gym.attendance.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
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
  private UserRepository userRepository;

  @Mock
  private AttendanceRepository attendanceRepository;

  @InjectMocks
  private AttendanceServiceImpl attendanceService;

  @Test
  void testAddAttendance() {
    Long userId = 1L;
    LocalDate date = LocalDate.now();

    User user = new User();
    user.setId(userId);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    attendanceService.addAttendance(userId, date);

    verify(attendanceRepository, times(1)).save(Mockito.any(Attendance.class));
  }

  @Test
  void testAddAttendanceUserNotFound() {
    Long userId = 1L;
    LocalDate date = LocalDate.now();

    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> attendanceService.addAttendance(userId, date));

    verify(attendanceRepository, Mockito.never()).save(Mockito.any(Attendance.class));
  }

  @Test
  void testCalculateAttendanceStreak() {
    Long userId = 1L;

    List<Attendance> attendances = createAttendances(userId);

    when(attendanceRepository.findByUserIdOrderByDateAsc(userId)).thenReturn(attendances);

    int streak = attendanceService.calculateAttendanceStreak(userId);

    assertEquals(7, streak);
  }

  private List<Attendance> createAttendances(Long userId) {
    List<Attendance> attendances = new ArrayList<>();
    attendances.add(createAttendance(userId, LocalDate.now().minusDays(7)));
    attendances.add(createAttendance(userId, LocalDate.now().minusDays(6)));
    attendances.add(createAttendance(userId, LocalDate.now().minusDays(5)));
    attendances.add(createAttendance(userId, LocalDate.now().minusDays(3)));
    attendances.add(createAttendance(userId, LocalDate.now().minusDays(2)));
    attendances.add(createAttendance(userId, LocalDate.now().minusDays(1)));
    attendances.add(createAttendance(userId, LocalDate.now()));
    return attendances;
  }


  private Attendance createAttendance(Long userId, LocalDate date) {
    Attendance attendance = new Attendance();
    attendance.setUser(new User(userId, "Test", "test@example.com", "password", new ArrayList<>()));
    attendance.setDate(date);
    return attendance;
  }

}
package space.deg.adam.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.deg.adam.domain.OverviewFilter;
import space.deg.adam.domain.user.User;
import space.deg.adam.repository.OverviewFilterRepository;

@Service
public class OverviewFilterService {
  @Autowired
  OverviewFilterRepository overviewFilterRepository;

  public OverviewFilter getFilter(User user) {
    OverviewFilter overviewFilter = overviewFilterRepository.findByUser(user);
    if (overviewFilter == null) {
      overviewFilter = new OverviewFilter();
      overviewFilter.setUser(user);
      overviewFilterRepository.save(overviewFilter);
    }

    return overviewFilterRepository.findByUser(user);
  }

  public void setup(User user, LocalDateTime fromDate, LocalDateTime toDate, Boolean showDots) {
    OverviewFilter overviewFilter = getFilter(user);
    overviewFilter.setup(fromDate, toDate, showDots);
    overviewFilterRepository.save(overviewFilter);
  }

  public void clearFilter(User user) {
    OverviewFilter overviewFilter = getFilter(user);
    overviewFilter.clear();
    overviewFilterRepository.save(overviewFilter);
  }

  public boolean isActive(User user) {
    return getFilter(user).getIsActive();
  }
}

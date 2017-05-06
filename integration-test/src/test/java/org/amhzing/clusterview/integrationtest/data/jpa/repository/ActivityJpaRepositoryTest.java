package org.amhzing.clusterview.integrationtest.data.jpa.repository;

import org.amhzing.clusterview.integrationtest.annotation.TestOffline;
import org.amhzing.clusterview.jpa.entity.ActivityEntity;
import org.amhzing.clusterview.jpa.repository.ActivityJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.integrationtest.helper.JpaRepositoryHelper.INITIAL_ACTIVITIES_SIZE;
import static org.amhzing.clusterview.integrationtest.helper.JpaRepositoryHelper.newActivity;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class ActivityJpaRepositoryTest {

    @Autowired
    private ActivityJpaRepository activityJpaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_get_activity() throws Exception {
        final ActivityEntity sc = activityJpaRepository.findOne("sct");

        assertThat(sc).isNotNull();
        assertThat(sc.getName()).isEqualTo("SC Tutor");
    }

    @Test
    public void should_insert_activity() throws Exception {
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);

        entityManager.persist(newActivity());

        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE + 1);
    }

    @Test
    public void should_delete_activity() throws Exception {
        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);

        final ActivityEntity activity = newActivity();
        entityManager.persist(activity); // +1
        entityManager.remove(activity);  // -1

        assertThat(allActivities()).hasSize(INITIAL_ACTIVITIES_SIZE);
    }

    private List<ActivityEntity> allActivities() {
        return activityJpaRepository.findAll();
    }
}
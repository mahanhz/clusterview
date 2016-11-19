package org.amhzing.clusterview.infra.jpa.repository;

import org.amhzing.clusterview.annotation.TestOffline;
import org.amhzing.clusterview.infra.jpa.mapping.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.amhzing.clusterview.helper.JpaRepositoryHelper.newActivity;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestOffline
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void should_get_activity() throws Exception {
        final Activity sc = activityRepository.findOne("SC");

        assertThat(sc).isNotNull();
        assertThat(sc.getName()).isEqualTo("Study Circle");
    }

    @Test
    public void should_insert_activity() throws Exception {
        assertThat(allActivities()).hasSize(5);

        entityManager.persist(newActivity());

        assertThat(allActivities()).hasSize(6);
    }

    @Test
    public void should_delete_activity() throws Exception {
        assertThat(allActivities()).hasSize(5);

        final Activity activity = newActivity();
        entityManager.persist(activity); // +1
        entityManager.remove(activity);  // -1

        assertThat(allActivities()).hasSize(5);
    }

    private List<Activity> allActivities() {
        return activityRepository.findAll();
    }
}
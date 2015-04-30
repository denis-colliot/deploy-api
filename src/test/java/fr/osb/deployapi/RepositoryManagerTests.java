package fr.osb.deployapi;

import fr.osb.deployapi.boot.Application;
import fr.osb.deployapi.model.BuildsNumbers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class RepositoryManagerTests {

    @Test
    public void getLatest() {
        final BuildsNumbers buildsNumbers = new BuildsNumbers();
        buildsNumbers.setBuildsNumbers(new ArrayList<>());

        buildsNumbers.getBuildsNumbers().add(new BuildsNumbers.BuildNumber("/15"));
        buildsNumbers.getBuildsNumbers().add(new BuildsNumbers.BuildNumber("/12"));
        buildsNumbers.getBuildsNumbers().add(new BuildsNumbers.BuildNumber("/187"));
        buildsNumbers.getBuildsNumbers().add(new BuildsNumbers.BuildNumber("/186"));
        buildsNumbers.getBuildsNumbers().add(new BuildsNumbers.BuildNumber("/153"));

        Assert.assertEquals(new Integer(187), buildsNumbers.getLatest().getNumber());
    }

}
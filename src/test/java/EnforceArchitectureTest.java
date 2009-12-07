
import com.seventytwomiles.architecturerules.AbstractArchitectureRulesConfigurationTest;

public final class EnforceArchitectureTest
        extends AbstractArchitectureRulesConfigurationTest
{

    @Override
    public void testArchitecture()
    {
        /**
         * Run the test via doTest(). If any rules are broken,
         * or if the configuration can not be loaded properly,
         * then the appropriate Exception will be thrown.
         */
        assertTrue(doTests());
    }

}

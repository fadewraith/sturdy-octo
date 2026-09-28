package distributed.transactions;

import java.util.ArrayList;
import java.util.List;

/**
 * Three-Phase Commit (3PC)
 * 
 * What it fixes over 2PC:
 * In 2PC, if the Coordinator fails after Phase 1 (Prepare) and before Phase 2 (Commit/Abort),
 * cohorts that voted YES are BLOCKED. They cannot commit (in case someone else voted NO) 
 * and cannot abort (in case everyone voted YES and the coordinator decided to commit before dying).
 * 
 * 3PC adds a "Pre-Commit" phase. 
 * Phase 1: CanCommit (Voting)
 * Phase 2: PreCommit (Coordinator tells everyone it intends to commit. If coordinator fails here, cohorts know everyone voted YES and can safely elect a new coordinator to finish committing).
 * Phase 3: DoCommit (Actual commit).
 * 
 * This breaks the blocking problem on coordinator failure, at the cost of an extra network round-trip.
 */
public class ThreePhaseCommit {

    static class Cohort {
        String name;
        boolean willFailCanCommit;
        boolean simulateTimeoutDuringPreCommit;

        public Cohort(String name, boolean willFailCanCommit, boolean simulateTimeoutDuringPreCommit) {
            this.name = name;
            this.willFailCanCommit = willFailCanCommit;
            this.simulateTimeoutDuringPreCommit = simulateTimeoutDuringPreCommit;
        }

        public boolean canCommit() {
            if (willFailCanCommit) {
                System.out.println("Cohort " + name + ": CanCommit? NO");
                return false;
            }
            System.out.println("Cohort " + name + ": CanCommit? YES");
            return true;
        }

        public boolean preCommit() {
            if (simulateTimeoutDuringPreCommit) {
                System.out.println("Cohort " + name + ": Timed out waiting for PreCommit (Coordinator failure simulated)");
                return false;
            }
            System.out.println("Cohort " + name + ": ACK PreCommit");
            return true;
        }

        public void doCommit() {
            System.out.println("Cohort " + name + ": DO COMMIT (Transaction Finalized)");
        }

        public void abort() {
            System.out.println("Cohort " + name + ": ABORT");
        }
    }

    static class Coordinator {
        List<Cohort> cohorts = new ArrayList<>();

        public void addCohort(Cohort c) {
            cohorts.add(c);
        }

        public void executeTransaction() {
            System.out.println("--- PHASE 1: CAN COMMIT ---");
            boolean allCanCommit = true;
            for (Cohort c : cohorts) {
                if (!c.canCommit()) {
                    allCanCommit = false;
                }
            }

            if (!allCanCommit) {
                System.out.println("Coordinator: Aborting transaction due to Phase 1 NO vote.");
                for (Cohort c : cohorts) c.abort();
                return;
            }

            System.out.println("--- PHASE 2: PRE-COMMIT ---");
            System.out.println("Coordinator: Broadcasting Pre-Commit to all cohorts.");
            boolean preCommitSuccess = true;
            for (Cohort c : cohorts) {
                if (!c.preCommit()) {
                    preCommitSuccess = false;
                    System.out.println("Coordinator/Recovery: Detected timeout. In a real 3PC, recovery protocol would take over.");
                    break;
                }
            }
            
            if (!preCommitSuccess) {
                 System.out.println("Coordinator: Aborting during Pre-Commit Phase due to timeout.");
                 for (Cohort c : cohorts) c.abort();
                 return;
            }

            System.out.println("--- PHASE 3: DO COMMIT ---");
            System.out.println("Coordinator: Broadcasting Do-Commit to all cohorts.");
            for (Cohort c : cohorts) {
                c.doCommit();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Three-Phase Commit (3PC) Simulation ===");
        
        System.out.println("\nScenario 1: Successful 3PC");
        Coordinator coord1 = new Coordinator();
        coord1.addCohort(new Cohort("DB_1", false, false));
        coord1.addCohort(new Cohort("DB_2", false, false));
        coord1.executeTransaction();

        System.out.println("\nScenario 2: Failure in Phase 1 (CanCommit)");
        Coordinator coord2 = new Coordinator();
        coord2.addCohort(new Cohort("DB_1", false, false));
        coord2.addCohort(new Cohort("DB_2", true, false));
        coord2.executeTransaction();
        
        System.out.println("\nScenario 3: Coordinator timeout during Phase 2 (PreCommit)");
        Coordinator coord3 = new Coordinator();
        coord3.addCohort(new Cohort("DB_1", false, false));
        coord3.addCohort(new Cohort("DB_2", false, true));
        coord3.executeTransaction();
    }
}

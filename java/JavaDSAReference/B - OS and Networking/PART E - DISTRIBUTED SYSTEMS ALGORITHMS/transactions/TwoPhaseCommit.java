package distributed.transactions;

import java.util.ArrayList;
import java.util.List;

public class TwoPhaseCommit {

    static class Cohort {
        String name;
        boolean willFailPrepare;

        public Cohort(String name, boolean willFailPrepare) {
            this.name = name;
            this.willFailPrepare = willFailPrepare;
        }

        public boolean prepare() {
            if (willFailPrepare) {
                System.out.println("Cohort " + name + ": Voted ABORT (Simulating failure/constraint violation)");
                return false;
            }
            System.out.println("Cohort " + name + ": Voted COMMIT");
            return true;
        }

        public void commit() {
            System.out.println("Cohort " + name + ": Transaction COMMITTED");
        }

        public void abort() {
            System.out.println("Cohort " + name + ": Transaction ABORTED");
        }
    }

    static class Coordinator {
        List<Cohort> cohorts = new ArrayList<>();

        public void addCohort(Cohort c) {
            cohorts.add(c);
        }

        public void executeTransaction() {
            System.out.println("--- PHASE 1: PREPARE ---");
            boolean allAgreed = true;
            for (Cohort c : cohorts) {
                boolean vote = c.prepare();
                if (!vote) {
                    allAgreed = false;
                    // In real 2PC, could fast-fail, but we'll collect votes for simulation
                }
            }

            System.out.println("--- PHASE 2: COMMIT/ABORT ---");
            if (allAgreed) {
                System.out.println("Coordinator: All cohorts voted COMMIT. Sending global COMMIT.");
                for (Cohort c : cohorts) {
                    c.commit();
                }
            } else {
                System.out.println("Coordinator: One or more cohorts voted ABORT. Sending global ABORT.");
                for (Cohort c : cohorts) {
                    c.abort();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Two-Phase Commit (2PC) Simulation ===");
        
        System.out.println("\nScenario 1: Successful Transaction");
        Coordinator coord1 = new Coordinator();
        coord1.addCohort(new Cohort("DB_Node_1", false));
        coord1.addCohort(new Cohort("DB_Node_2", false));
        coord1.executeTransaction();

        System.out.println("\nScenario 2: Transaction Failure (Node 2 Aborts)");
        Coordinator coord2 = new Coordinator();
        coord2.addCohort(new Cohort("DB_Node_1", false));
        coord2.addCohort(new Cohort("DB_Node_2", true));
        coord2.executeTransaction();
    }
}

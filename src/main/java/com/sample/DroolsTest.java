package com.sample;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * This is a sample class to launch a rule.
 */
public final class DroolsTest {

    /**
     * private.
     */
    private DroolsTest() {
        super();
    }

    /**
     * blah.
     * @param args param
     */
    public static void main(final String[] args) {
        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession =
                    kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger =
                 KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            // go !
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            ksession.insert(message);
            ksession.fireAllRules();
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * blah.
     * @return val
     * @throws Exception thrown
     */
    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder =
                KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(
                            "Sample.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

    /**
     * blah.
     * @author Barnaby Golden
     *
     */
    public static class Message {

        /**
         * blah.
         */
        public static final int HELLO = 0;
        /**
         * blah.
         */
        public static final int GOODBYE = 1;
        /**
         * blah.
         */
        private String message;
        /**
         * blah.
         */
        private int status;

        /**
         * blah.
         * @return val
         */
        public final String getMessage() {
            return this.message;
        }

        /**
         * blah.
         * @param message param
         */
        public final void setMessage(final String message) {
            this.message = message;
        }

        /**
         * blah.
         * @return val
         */
        public final int getStatus() {
            return this.status;
        }

        /**
         * blah.
         * @param status param
         */
        public final void setStatus(final int status) {
            this.status = status;
        }

    }
}

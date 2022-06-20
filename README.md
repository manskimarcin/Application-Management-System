# Application-Management-System
Simple backend of the application management system created using springboot

Done:
- created model with following fields:
        long id;
        String name
        String content
        State state -> it's an Enum with values:
            CREATED,
            VERIFIED,
            ACCEPTED,
            PUBLISHED,
            DELETED,
            REJECTED
        String rejectionReason;
        Long publicationId;

- created REST endpoints:
    POST ("/applications")
    it's possible to create new application, automatically it gives state = CREATED,
    id (unique)name and content is being saved to DB and the

    GET ("/applications")
    List of all applications is being received with sorting by name and state (alphabetically) and
    page size is hardcoded to 10

    PUT ("/applications")
    This type of request is used to change application state, it's done according to specification

- added all necessary logic to switch between states according to specification in ApplicationService.class

Haven't done:
- history of all transition between states is not persisted
- unit tests

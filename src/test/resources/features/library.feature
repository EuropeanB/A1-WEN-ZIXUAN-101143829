Feature: Library Book Borrowing

  Background:
    Given The library system is initialized

  Scenario Outline: A1_scenario
    When user "<user>" logs in
    Then user "<user>" can borrow the book "<bookTitle>" and borrowed it
    And user "<user>" has 1 book that need to return

    # A borrowed book becomes unavailable to other users
    # Only one user can have a book at a time
    When user "<user>" logs out
    And user "<otherUser>" logs in
    Then user "<otherUser>" cannot borrow the book "<bookTitle>"

    # A returned book becomes available again
    When user "<otherUser>" logs out
    And user "<user>" logs in
    And user "<user>" returns book "<bookTitle>"
    Then user "<user>" has 0 book that need to return

    When user "<user>" logs out
    And user "<otherUser>" logs in
    Then user "<otherUser>" can borrow the book "<bookTitle>" and borrowed it
    And user "<otherUser>" has 1 book that need to return


    Examples:
      | user     | bookTitle              | otherUser |
      | alice    | The Great Gatsby       | bob       |
      | bob      | To Kill a Mockingbird  | charlie   |
      | charlie  | 1984                   | alice     |

  Scenario Outline: multiple_holds_queue_processing
    When user "<user1>" logs in
    Then user "<user1>" can borrow the book "<bookTitle>" and borrowed it
    And user "<user1>" has 1 book that need to return

    # Users can place holds on unavailable books
    When user "<user1>" logs out
    And user "<user2>" logs in
    Then user "<user2>" cannot borrow the book "<bookTitle>"
    And user "<user2>" can place a hold on book "<bookTitle>"'s queue

    # Users can place holds on unavailable books
    When user "<user2>" logs out
    And user "<user3>" logs in
    Then user "<user3>" cannot borrow the book "<bookTitle>"
    And user "<user3>" can place a hold on book "<bookTitle>"'s queue

    # Hold queue follows FIFO ordering
    # Only the notified user can borrow the reserved book
    When user "<user3>" logs out
    And user "<user1>" logs in
    And user "<user1>" returns book "<bookTitle>"
    Then user "<user1>" has 0 book that need to return

    When user "<user1>" logs out
    And user "<user3>" logs in
    Then user "<user3>" should not be notified
    And user "<user3>" cannot borrow the book "<bookTitle>"

    # Notifications are sent to the correct user when book becomes available
    # Queue advances properly when reserved books are borrowed or returned
    When user "<user3>" logs out
    And user "<user2>" logs in
    Then user "<user2>" should be notified that "<bookTitle>" is available
    And user "<user2>" can borrow the book "<bookTitle>" and borrowed it
    And user "<user2>" has 1 book that need to return


    # Notifications are sent to the correct user when book becomes available
    # Queue advances properly when reserved books are borrowed or returned
    When user "<user2>" returns book "<bookTitle>"
    Then user "<user2>" has 0 book that need to return

    When user "<user2>" logs out
    And user "<user3>" logs in
    Then user "<user3>" should be notified that "<bookTitle>" is available
    And user "<user3>" can borrow the book "<bookTitle>" and borrowed it
    And user "<user3>" has 1 book that need to return

    Examples:
      | user1   | user2   | user3   | bookTitle |
      | alice   | bob     | charlie | Pride and Prejudice     |
      | bob     | charlie | alice   | The Hobbit              |
      | charlie | alice   | bob     | Harry Potter            |

  Scenario Outline: borrowing_limit_and_hold_interactions
    # Users cannot exceed the 3-book borrowing limit
    # Users can place holds even when at the borrowing limit
    When user "<user1>" logs in
    Then user "<user1>" can borrow the book "<book1>" and borrowed it
    And user "<user1>" has 1 book that need to return
    And user "<user1>" can borrow the book "<book2>" and borrowed it
    And user "<user1>" has 2 book that need to return
    And user "<user1>" can borrow the book "<book3>" and borrowed it
    And user "<user1>" has 3 book that need to return
    And user "<user1>" borrowed count is max
    And user "<user1>" cannot borrow the book "<targetBook>"
    And user "<user1>" can place a hold on book "<targetBook>"'s queue

    # Another user borrows the held book
    When user "<user1>" logs out
    And user "<user2>" logs in
    Then user "<user2>" can borrow the book "<targetBook>" and borrowed it

    # When that user returns it, the hold user receives notification
    When user "<user2>" returns book "<targetBook>"
    And user "<user2>" logs out
    And user "<user1>" logs in
    Then user "<user1>" should be notified that "<targetBook>" is available

    # User drops below limit after returning one book
    When user "<user1>" returns book "<book3>"
    And user "<user1>" borrowed count is 2
    Then user "<user1>" can borrow the book "<targetBook>" and borrowed it

    Examples:
      | user1   | user2   | book1                  | book2             | book3                  | targetBook             |
      | alice   | bob     | The Catcher in the Rye | Animal Farm       | Lord of the Flies      | Jane Eyre              |
      | bob     | charlie | Animal Farm            | Lord of the Flies | Jane Eyre              | The Catcher in the Rye |
      | charlie | alice   | Lord of the Flies      | Jane Eyre         | The Catcher in the Rye | Animal Farm            |

  Scenario Outline: no_books_borrowed_scenario
    When user "<user1>" logs in
    Then user "<user1>" has no book need to return
    And all books are available

    When user "<user1>" logs out
    And user "<user2>" logs in
    Then user "<user2>" has no book need to return
    And all books are available

    When user "<user2>" logs out
    And user "<user3>" logs in
    Then user "<user3>" has no book need to return
    And all books are available

    Examples:
      | user1     | user2 | user3 |
      | alice    | bob      | charlie  |
      | bob      | charlie  | alice    |
      | charlie  | alice    | bob      |


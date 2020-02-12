INSERT INTO `BOOK_MEMBER` (`MBR_ID`, `LOGIN_ID`, `PWD`, `NAME`, `ROLE`, `CREUSR`, `CREDT`)
VALUES
  (1, 'testuser', '$2a$10$xTFWxE.h2jUGRz/SfBxo8umzwNaOeji7WDiwrSzbqeefVFKDcP9RS', '이동진', 'MEMBER', 'testuser',
   '2020-02-10 20:15:01'),
  (2, 'testuser2', '$2a$10$xTFWxE.h2jUGRz/SfBxo8umzwNaOeji7WDiwrSzbqeefVFKDcP9RS', '테스터', 'MEMBER', 'testuser2',
   '2020-02-10 21:17:21');

INSERT into SEARCH_HISTORY (HIST_ID, KEYWORD, MBR_ID, `CREUSR`, `CREDT`)
VALUES
  (1, '아빠', 1, 'testuser', '2020-02-08 20:15:01'),
  (3, '딸', 1, 'testuser', '2020-02-10 20:15:01'),
  (2, '엄마', 1, 'testuser', '2020-02-09 20:15:01'),
  (4, '할비', 2, 'testuser', '2020-02-09 20:15:01'),
  (5, '아빠', 2, 'testuser', '2020-02-09 20:15:01'),
  (6, '할미', 2, 'testuser', '2020-02-10 20:15:01');


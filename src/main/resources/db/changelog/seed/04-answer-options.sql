-- Q1
INSERT INTO answer_options (question_id, text, is_correct) VALUES
                                                               (1, 'null', false),
                                                               (1, 'object', true),
                                                               (1, 'undefined', false),
                                                               (1, 'number', false);

-- Q4
INSERT INTO answer_options (question_id, text, is_correct) VALUES
                                                               (4, '0', true),
                                                               (4, '"" (пустая строка)', true),
                                                               (4, '[]', false),
                                                               (4, 'null', true),
                                                               (4, '{}', false),
                                                               (4, 'undefined', true);

-- Q9
INSERT INTO answer_options (question_id, text, is_correct) VALUES
                                                               (9, '1, 2, 3, 4', false),
                                                               (9, '1, 4, 3, 2', true),
                                                               (9, '1, 4, 2, 3', false),
                                                               (9, '1, 3, 4, 2', false);

-- Q14
INSERT INTO answer_options (question_id, text, is_correct) VALUES
                                                               (14, 'useState', false),
                                                               (14, 'useContext', false),
                                                               (14, 'useEffect', true),
                                                               (14, 'useReducer', false);

-- Q17
INSERT INTO answer_options (question_id, text, is_correct) VALUES
                                                               (17, 'useEffect', true),
                                                               (17, 'useMemo', true),
                                                               (17, 'useFetch', false),
                                                               (17, 'useCallback', true),
                                                               (17, 'useLocalStorage', false);

-- Q22
INSERT INTO answer_options (question_id, text, is_correct) VALUES
                                                               (22, 'null', false),
                                                               (22, 'undefined', false),
                                                               (22, 'TypeError', true),
                                                               (22, '"" (пустая строка)', false);

-- Q26
INSERT INTO answer_options (question_id, text, is_correct) VALUES
                                                               (26, 'git commit --amend', true),
                                                               (26, 'git commit --fixup', false),
                                                               (26, 'git rebase -i', false),
                                                               (26, 'git reset --soft HEAD~1', false);

-- Q29
INSERT INTO answer_options (question_id, text, is_correct) VALUES
                                                               (29, 'unknown', false),
                                                               (29, 'never', false),
                                                               (29, 'any', true),
                                                               (29, 'void', false);
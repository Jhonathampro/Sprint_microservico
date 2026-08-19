INSERT INTO aluno (nome, cpf, email) VALUES ('Jon Snow', '12345678963', 'jon.snow@academico.com');
INSERT INTO aluno (nome, cpf, email) VALUES ('Arya Stark', '86245712365', 'arya.stark@academico.com');

INSERT INTO professor (nome, cpf, email) VALUES ('Ned Stark', '11122233344', 'ned.stark@academico.com');
INSERT INTO professor (nome, cpf, email) VALUES ('Tyrion Lannister', '55566677788', 'tyrion.lannister@academico.com');

INSERT INTO turma (nome, turno, vagas, id_professor) VALUES ('Estruturas de Dados', 'MANHA', 30, 1);
INSERT INTO turma (nome, turno, vagas, id_professor) VALUES ('Engenharia de Software', 'NOITE', 40, 2);

INSERT INTO aula (data_aula, horario_inicio, horario_fim, id_turma) VALUES ('2026-03-01', '08:00', '10:00', 1);
INSERT INTO aula (data_aula, horario_inicio, horario_fim, id_turma) VALUES ('2026-03-01', '19:00', '21:00', 2);

INSERT INTO matricula (data_matricula, status, id_aluno, id_turma) VALUES ('2026-02-15', 'ATIVA', 1, 1);
INSERT INTO matricula (data_matricula, status, id_aluno, id_turma) VALUES ('2026-02-16', 'ATIVA', 2, 2);

INSERT INTO frequencia (id_aula, id_matricula, situacao) VALUES (1, 1, 'PRESENTE');
INSERT INTO frequencia (id_aula, id_matricula, situacao) VALUES (2, 2, 'PRESENTE');

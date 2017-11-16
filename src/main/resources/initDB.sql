CREATE TABLE companies
(
  idcompanies  INT AUTO_INCREMENT
    PRIMARY KEY,
  companieName VARCHAR(45) NOT NULL,
  CONSTRAINT idcompanies_UNIQUE
  UNIQUE (idcompanies)
);
CREATE TABLE customers
(
  idcustomers  INT AUTO_INCREMENT
    PRIMARY KEY,
  customerName VARCHAR(45) NOT NULL,
  CONSTRAINT `id customers_UNIQUE`
  UNIQUE (idcustomers)
);
CREATE TABLE developer
(
  iddeveloper     INT AUTO_INCREMENT
    PRIMARY KEY,
  firstName       VARCHAR(45)            NOT NULL,
  lastName        VARCHAR(45)            NOT NULL,
  experianse      INT(1) DEFAULT '0'     NULL,
  workOnProjectid INT                    NULL,
  selery          DECIMAL(5) DEFAULT '0' NULL,
  CONSTRAINT idproject
  FOREIGN KEY (workOnProjectid) REFERENCES projects (idproject)
);
CREATE INDEX idproject_idx
  ON developer (workOnProjectid);

CREATE TABLE developersskills
(
  developer INT NOT NULL,
  Skill     INT NOT NULL,
  CONSTRAINT iddeveloper
  FOREIGN KEY (developer) REFERENCES developer (iddeveloper),
  CONSTRAINT idskill
  FOREIGN KEY (Skill) REFERENCES skill (idskill)
);
CREATE INDEX iddeveloper_idx
  ON developersskills (developer);
CREATE INDEX idskill_idx
  ON developersskills (Skill);

CREATE TABLE projects
(
  idproject   INT AUTO_INCREMENT
    PRIMARY KEY,
  projectName VARCHAR(45)         NOT NULL,
  idcostomer  INT                 NOT NULL,
  idcompanie  INT                 NOT NULL,
  cost        DECIMAL DEFAULT '0' NOT NULL,
  CONSTRAINT idproject_UNIQUE
  UNIQUE (idproject),
  CONSTRAINT idcostomer
  FOREIGN KEY (idcostomer) REFERENCES customers (idcustomers),
  CONSTRAINT idcompanie
  FOREIGN KEY (idcompanie) REFERENCES companies (idcompanies)
);
CREATE INDEX idcompanie_idx
  ON projects (idcompanie);
CREATE INDEX idcostomer_idx
  ON projects (idcostomer);

CREATE TABLE skill
(
  idskill INT AUTO_INCREMENT
    PRIMARY KEY,
  skill   VARCHAR(45) NOT NULL,
  CONSTRAINT skill_UNIQUE
  UNIQUE (skill),
  CONSTRAINT idskill_UNIQUE
  UNIQUE (idskill)
);
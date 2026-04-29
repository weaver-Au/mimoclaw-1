-- Sample data for testing
USE exam_system;

-- More sample users
INSERT IGNORE INTO users(username, password, real_name, phone, class_name, role) VALUES('teacher2', '123456', '李老师', '13800002222', NULL, 'TEACHER');
INSERT IGNORE INTO users(username, password, real_name, phone, class_name, role) VALUES('student2', '123456', '王同学', '13800003333', '计算机2班', 'STUDENT');
INSERT IGNORE INTO users(username, password, real_name, phone, class_name, role) VALUES('student3', '123456', '赵同学', '13800004444', '计算机1班', 'STUDENT');

-- Sample courses
INSERT IGNORE INTO courses(id, name, teacher_id, description) VALUES(1, 'Java程序设计', 2, 'Java语言基础与面向对象编程');
INSERT IGNORE INTO courses(id, name, teacher_id, description) VALUES(2, '数据结构与算法', 2, '常见数据结构和算法分析');
INSERT IGNORE INTO courses(id, name, teacher_id, description) VALUES(3, '数据库原理', 2, '关系数据库设计与SQL');

-- Sample questions for Java course
INSERT IGNORE INTO questions(id, course_id, type, content, options, answer, analysis, difficulty, creator_id) VALUES
(1, 1, 'SINGLE', 'Java中哪个关键字用于定义类？', '["class","Class","define","struct"]', 'A', 'Java使用class关键字定义类', 1, 2),
(2, 1, 'SINGLE', '以下哪个不是Java的基本数据类型？', '["int","boolean","String","double"]', 'C', 'String是引用类型，不是基本数据类型', 1, 2),
(3, 1, 'MULTI', '以下哪些是Java的访问修饰符？', '["public","private","protected","internal"]', 'A,B,C', 'Java有三种访问修饰符：public、private、protected', 2, 2),
(4, 1, 'JUDGE', 'Java支持多继承。', '["正确","错误"]', '错误', 'Java不支持类的多继承，但支持接口的多实现', 1, 2),
(5, 1, 'SINGLE', 'Java中main方法的正确签名是？', '["public static void main(String[] args)","void main(String args)","public void main(String[] args)","static void main(String[] args)"]', 'A', 'main方法必须是public static void且参数为String[]', 1, 2);

-- Sample questions for Data Structures course
INSERT IGNORE INTO questions(id, course_id, type, content, options, answer, analysis, difficulty, creator_id) VALUES
(6, 2, 'SINGLE', '栈的特点是？', '["先进先出","先进后出","随机访问","按优先级出队"]', 'B', '栈是后进先出(LIFO)的数据结构', 1, 2),
(7, 2, 'JUDGE', '二叉树的前序遍历顺序是：根-左-右。', '["正确","错误"]', '正确', '前序遍历先访问根节点，再遍历左子树，最后遍历右子树', 1, 2),
(8, 2, 'SINGLE', '快速排序的平均时间复杂度是？', '["O(n)","O(n log n)","O(n²)","O(log n)"]', 'B', '快速排序平均时间复杂度为O(n log n)', 2, 2);

-- Sample paper
INSERT IGNORE INTO papers(id, name, course_id, creator_id, total_score, duration, published) VALUES(1, 'Java基础测试', 1, 2, 50, 60, 1);
INSERT IGNORE INTO papers(id, name, course_id, creator_id, total_score, duration, published) VALUES(2, '数据结构期中测试', 2, 2, 30, 45, 1);

-- Link questions to papers
INSERT IGNORE INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(1, 1, 1, 10);
INSERT IGNORE INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(1, 2, 2, 10);
INSERT IGNORE INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(1, 3, 3, 10);
INSERT IGNORE INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(1, 4, 4, 10);
INSERT IGNORE INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(1, 5, 5, 10);
INSERT IGNORE INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(2, 6, 1, 10);
INSERT IGNORE INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(2, 7, 2, 10);
INSERT IGNORE INTO paper_questions(paper_id, question_id, sort_order, score) VALUES(2, 8, 3, 10);

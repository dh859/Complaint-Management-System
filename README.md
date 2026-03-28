# 📌 Online Complaint Management System (OCMS)
A full-stack web application designed to streamline complaint submission, tracking, and resolution using a centralized, automated, and transparent workflow. Built with React, Spring Boot, and MySQL, this system modernizes traditional complaint-handling processes by offering role-based access, real-time tracking, automated routing, reporting, and secure data management.

---

# 📑 Table of Contents
1. [Abstract](#1-abstract)  
2. [Problem Domain](#2-problem-domain)  
3. [About the Project](#3-about-the-project)  
4. [Objectives](#4-objectives)  
5. [Key Features](#5-key-features)  
6. [Project Category & Beneficiaries](#6-project-category--beneficiaries)  
7. [Feasibility Study](#7-feasibility-study)  
8. [Methodology & Planning](#8-methodology--planning)  
9. [Tools & Technologies](#9-tools--technologies)  
10. [Platform Requirements](#10-platform-requirements)  
11. [System Architecture](#11-system-architecture)  
12. [Module Description](#12-module-description)  
13. [ER Diagram & Data Tables](#13-er-diagram--data-tables)  
14. [Future Scope](#14-future-scope)  
15. [Conclusion](#15-conclusion)  
16. [References](#16-references)

---

# 1️⃣ Abstract
The **Online Complaint Management System (OCMS)** simplifies the complaint handling process through automation, structured workflows, and real-time tracking.

It provides:
- Instant complaint submission  
- Automated routing  
- Priority classification  
- Real-time updates  
- Reporting & analytics  

This results in improved **efficiency, accountability, transparency,** and overall **user satisfaction**.

---

# 2️⃣ Problem Domain
Traditional complaint systems suffer from:
- Manual paperwork  
- Lack of tracking visibility  
- Delayed resolution  
- Unstructured workflows  
- No analytics for improvements  
- No accountability mechanisms  

OCMS replaces these issues through a **centralized, structured, and transparent digital system**.

---

# 3️⃣ About the Project
The OCMS is a full-stack web system that:
- Centralizes all complaints  
- Tracks each complaint lifecycle  
- Automates routing & prioritization  
- Helps administrators monitor, assign, and resolve issues  
- Generates insights through powerful reporting  

---

# 4️⃣ Objectives
The platform aims to achieve:

### ✔ 1. Centralization
A unified system for all complaint submissions.

### ✔ 2. Workflow Automation
Automatic routing, category allocation, and priority setting.

### ✔ 3. Transparency
Real-time tracking + instant notifications.

### ✔ 4. Analytical Reporting
KPIs like complaint volume, category breakdown, resolution time.

### ✔ 5. Efficiency
Reduction in average time-to-resolution.

### ✔ 6. Accountability
Role-based permissions and logs.

### ✔ 7. User Experience
Modern, intuitive, responsive UI/UX.

---

# 5️⃣ Key Features
1. **Modular Architecture**  
2. **Centralized Dashboard**  
3. **Data Integration & Consistency**  
4. **Reports & Analytics**  
5. **Scalable & Flexible Design**  
6. **Modern UI (React + Tailwind)**  
7. **Cost-Effective Open-Source Stack**

---

# 6️⃣ Project Category & Beneficiaries

## 📂 Category
Digital Transformation – E-Service Delivery / Enterprise Application

## 👥 Beneficiaries

### 1. Organization / Administration
- Reduced overhead  
- Improved workflow tracking  
- Highly transparent complaint management  
- Data-driven decisions  

### 2. End Users (Citizens / Customers)
- Faster responses  
- Real-time updates  
- Easy-to-use interface  

---

# 7️⃣ Feasibility Study

## ✔ A. Technical Feasibility
- Uses scalable technologies (React, Spring Boot, MySQL)  
- REST API architecture  
- Secure authentication  

## ✔ B. Economic Feasibility
- Reduces manual labor  
- High ROI  
- Automation saves time & cost  

## ✔ C. Operational Feasibility
- Easy to adopt  
- Minimal training  
- Improves service quality  

---

# 8️⃣ Methodology & Planning

## 🟦 Agile Methodology
- Incremental development  
- Regular feedback  
- Small iterative sprints  
- Collaborative approach  

## 🟩 Planning Phases
1. Requirement Gathering  
2. System Design (DFD, ERD, Architecture)  
3. Module-wise Development  
4. Testing (Unit & Integration)  
5. Deployment & Documentation  

---

# 9️⃣ Tools & Technologies

## 🖥 Languages
- Java (JDK 21+)  
- JavaScript  

## ⚙ Frameworks
- Spring Boot  
- React JS  
- Tailwind CSS  

## 🗄 Database
- MySQL  
- JPA/Hibernate  

## 🔧 Development Tools
- VS Code  
- Git & GitHub  

---

# 1️⃣0️⃣ Platform Requirements

## 🖥 Hardware
- 8 vCPUs  
- 16 GB RAM  
- 500 GB SSD  
- 1 Gbps network  

## 💽 Software
- Ubuntu Server 22.04  
- MySQL 8+  
- JDK 21+  
- Nginx/Apache  
- Git  

---
## 🖼 DFD 
![DFD Level 0](images/CMS0Level.png)

![DFD Level 1](images/CMS1Level.png)

---

# 1️⃣1️⃣ System Architecture

## 🧱 Layers
1. **Frontend (React)**
2. **Security (Spring Security + JWT)**
3. **Controllers (REST APIs)**
4. **Service Layer (Business Logic)**
5. **Repository Layer (JPA)**
6. **Database (MySQL)**

## 🔁 Request Flow
User → Frontend → JWT Security → Controller → Service → Repository → Database → Response  

---

# 1️⃣2️⃣ Module Description

## 🔹 1. User Submission & Tracking
- Complaint registration  
- File upload  
- Status tracking  
- Notification alerts  

## 🔹 2. Administrator Dashboard
- Manage complaints  
- Auto-routing  
- SLA monitoring  
- Update complaint statuses  

## 🔹 3. Authentication & Security
- JWT-based login  
- Role-based access (Admin, Manager, Agent, User)  

## 🔹 4. Reporting & Analytics
- KPIs  
- Filters (dates, categories, agents)  
- Export options (CSV/PDF)  

## 🔹 5. Notification Module
- Email alerts  
- In-app notifications  
- Template-based messages  

---

# 1️⃣3️⃣ ER Diagram & Data Tables

![ERD](images/ER.jpeg)

### 📌 Major Tables:
- **complaint**  
- **users**  
- **external_department**  
- **query_files**  
- **reports**  
- **refresh_token**  

Each table includes detailed fields for:
- Relations  
- Categories  
- Status  
- Priority  
- Metadata  

---

# 1️⃣4️⃣ Future Scope
1. AI-based analytics  
2. Mobile app (Android/iOS)  
3. Cross-platform integration  
4. Biometric authentication  
5. Cloud scaling  
6. End-to-end encryption  

---

# 1️⃣5️⃣ Conclusion
The **Online Complaint Management System (OCMS)** digitizes and streamlines complaint management by enabling automation, transparency, reporting, and accountability.

With modern technology integration and future scalability plans, OCMS stands as a strong solution for digital service improvements.

---

# 1️⃣6️⃣ References
- Spring Framework Documentation  
- Baeldung (Spring Boot Guides)  
- TutorialsPoint & GeeksforGeeks  
- Research Papers on Data Security  
- Classroom Notes & Faculty Guidance  

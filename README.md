# Finora — Personal Finance Management System

Finora is a full-stack personal finance management application designed to help users manage their finances through a centralized dashboard. It provides user authentication and tools to track transactions, manage budgets, and organize financial information.

## 🌐 Live Application

- **Frontend:** https://finora-delta-one.vercel.app
- **Backend API:** https://finora-backend-29ay.onrender.com
- **GitHub Repository:** https://github.com/Alekhyaenuguru/Finora

## ✨ Features

- **User Authentication:** User registration and login.
- **Dashboard:** View financial information in one place.
- **Transaction Management:** Manage financial transactions.
- **Budget Management:** Create and manage budgets.
- **Profile Management:** Access and manage user profile information.
- **Database Integration:** Store application data using MySQL.
- **Full-Stack Architecture:** React frontend integrated with a Spring Boot backend.
- **Cloud Deployment:** Frontend and backend hosted separately.

## 🛠️ Technology Stack

| Component | Technology |
|---|---|
| Frontend | React.js, Vite |
| Backend | Java 21, Spring Boot |
| Database | MySQL |
| Frontend Hosting | Vercel |
| Backend Hosting | Render |
| Database Hosting | Aiven |
| Version Control | Git and GitHub |

## 🏗️ Project Architecture

```text
                    Finora
                       |
          +------------+------------+
          |                         |
     React + Vite              Spring Boot
       Frontend                  Backend
          |                         |
        Vercel                    Render
                                    |
                                  MySQL
                                  Aiven
```

The frontend communicates with the backend through HTTP requests. The backend handles application logic, authentication, and database operations.

## 📂 Project Structure

```text
Finora/
├── frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── vite.config.js
│
├── backend/
│   └── backend/
│       ├── src/
│       ├── pom.xml
│       └── ...
│
└── README.md
```

*Note: The displayed structure highlights the main project directories. Individual files may differ in your repository.*

## 🚀 Getting Started

### Prerequisites

Install the following software before running the project locally:

- Node.js and npm
- Java Development Kit (JDK) 21
- MySQL database or a compatible MySQL database service
- Git
- Maven, if not provided through the project's Maven Wrapper

### 1. Clone the Repository

```bash
git clone https://github.com/Alekhyaenuguru/Finora.git
cd Finora
```

### 2. Run the Frontend

Open a terminal in the project root and execute:

```bash
cd frontend
npm install
npm run dev
```

Open the local URL printed by Vite in your terminal, usually `http://localhost:5173`.

### 3. Configure the Backend

Open another terminal:

```bash
cd backend/backend
```

Configure the required database connection and authentication settings using the project's existing Spring Boot configuration.

For local development, use environment variables or a local configuration file that is excluded from Git. Do not commit database passwords, JWT secrets, or other credentials.

Ensure that the configured MySQL database exists and is accessible before starting the backend.

### 4. Start the Backend

If Maven is installed:

```bash
mvn spring-boot:run
```

Alternatively, if the project includes the Maven Wrapper, use the appropriate wrapper command for your operating system.

The backend's local port depends on the Spring Boot configuration.

## ☁️ Deployment

Finora uses separate hosting services for its application components:

- **Vercel:** Hosts the React frontend.
- **Render:** Hosts the Spring Boot backend.
- **Aiven:** Provides the MySQL database service.

Deployment requires the frontend to use the correct backend API URL and the backend to have valid database credentials, authentication secrets, and permitted frontend origins.

## 🔐 Security Considerations

- Never publish database credentials, API secrets, or JWT signing keys.
- Keep private environment variables in the hosting provider's environment settings.
- Validate and authorize requests on the backend.
- Restrict cross-origin access to the required frontend origins.
- Do not commit local environment files containing secrets.

## 🧪 Testing and Verification

The application should be tested for the following workflows:

- User registration and login
- Dashboard loading
- Transaction creation and retrieval
- Budget creation and management
- Profile access and updates
- Logout and session handling
- Frontend-to-backend communication
- Database persistence

Actual test results should be recorded after executing each workflow.

## 📸 Screenshots

Add screenshots of the actual running application to this section.

Suggested screenshots:

1. Login and registration pages
2. Main dashboard
3. Transaction management
4. Budget management
5. User profile

You can store the screenshots in a folder such as `docs/screenshots/` and reference them here using relative image paths.

## 🔮 Future Enhancements

Potential future improvements include:

- Responsive navigation improvements for mobile devices
- Financial reports and visual analytics
- Monthly spending summaries
- Budget alerts and notifications
- Exportable financial reports
- Additional security and automated testing

## 👨‍💻 Project Information

**Project Name:** Finora  
**Category:** Full-Stack Web Application  
**Purpose:** Personal finance management  
**Repository:** https://github.com/Alekhyaenuguru/Finora

---

Finora brings financial tracking and budget management together in a single web application.

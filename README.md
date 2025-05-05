💰 Expense Tracker - Smart Personal Finance Manager
App Logo

A modern Android app for tracking expenses, scanning receipts with AI, and gaining financial insights. Built with Jetpack Compose and Firebase.

✨ Key Features
📊 Financial Management
Add/edit transactions with categories (Food, Transport, Health, etc.)

View balance summary with income/expense breakdown

Transaction history with filtering (day/week/month/year)

🔍 AI-Powered Receipt Scanning
Camera integration for invoice capture

Google Gemini AI extracts:

Vendor details (name, address)

Transaction date & total amount

Itemized purchases with quantities/prices

Automatic category assignment

📈 Data Visualization
Interactive pie charts for spending categories

Monthly expense trends with bar charts

Balance history tracking

☁️ Cloud Sync & Security
Firebase Authentication (Email/Google)

Real-time sync with Cloud Firestore

Encrypted data storage

🛠️ Technical Stack
Frontend:

Jetpack Compose (Modern UI)

CameraX (Image Capture)

ViewModel + Coroutines (State Management)

Navigation Compose

Backend:

Firebase Authentication

Cloud Firestore (NoSQL Database)

AI Integration:

Google Gemini API (Text extraction)

JSON parsing for structured data

📸 Screenshots
Home Screen	Receipt Scanning	Statistics
Home	Scan	Stats
🚀 Installation
Clone the repository:

bash
git clone https://github.com/enori-ben/Expense-Tracker.git
Open in Android Studio

Firebase Setup:

Add your google-services.json to app/ folder

Enable Email/Google authentication in Firebase Console

Configure API Key:
Add Gemini API key in local.properties:

API_KEY=your_api_key_here
Build and run the app

🔮 Future Roadmap
Budget planning system

Recurring transactions

PDF/Excel report generation

Multi-currency support

Biometric authentication

🤝 Contributing
We welcome contributions! Please:

Fork the repository

Create a feature branch

Submit a pull request

Follow Kotlin coding conventions and:

Keep composables modular

Use ViewModel for business logic

Add tests for new features

📄 License
MIT License - See LICENSE for details

📧 Contact
EnNori - enoridz11@gmail.com


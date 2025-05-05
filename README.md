# 💰 Expense Tracker


[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg)](https://kotlinlang.org)
[![Compose Version](https://img.shields.io/badge/Jetpack%20Compose-1.10.0-brightgreen)](https://developer.android.com/jetpack/compose)
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?logo=firebase\&logoColor=black\&style=flat-square)](https://firebase.google.com/)
[![Google Gemini API](https://img.shields.io/badge/Google%20Gemini-4285F4?logo=google\&logoColor=white\&style=flat-square)](https://gemini.google.com/)

 AI-powered Expense Tracker with Smart Receipt Scanning using Google Gemini API and OCR integration

## 🌟 Key Features

Expense Tracker helps you stay on top of your money with features like:

### 📊 Smart Transaction Management
*   ✅ **Add & Edit Transactions**: Easily log your income and expenses.
*   ✅ **Detailed Categorization**: Organize transactions across predefined categories (Food, Health, Transport, and more!).
*   ✅ **Real-time Balance**: See your total balance, income, and expenses updated instantly.
*   ✅ **Flexible Period Filters**: View transactions by day, week, month, or year to analyze trends.
*   ✅ **Transaction Details**: Tap into any transaction to see its full breakdown, including invoice details if scanned.

### 🔍 AI Receipt Scanning (Google Gemini)

*   📸 Scan receipts using CameraX.
*   ✨ Automatically extract vendor, amount, date, and items.
*   🏷️ Smartly categorize expenses.
*   📄 Get structured invoice data from scans.
*   📊 AI insights for spending patterns.

### 📈 Interactive Analytics

*   📊 Pie charts to see spending distribution.
*   🔍 Category breakdown for insights.
*   💡 Quick view of financial health.

### 🔒 Secure Cloud Sync (Firebase)

*   🔐 Sign in securely (Email/Password, Google).
*   🔄 Data syncs in real-time across devices with Cloud Firestore.
*   🛡️ Your financial data is kept secure.

## 🛠 Technical Stack

Expense Tracker is built using cutting-edge Android development technologies:

*   **Frontend**:
    *   **Jetpack Compose (UI)**.
    *   **Adobe Figma (UI Design)**.
    *   **ViewModel & Coroutines**: Implementing the MVVM pattern for robust state management and asynchronous operations.
    *   **Navigation Compose**: Handling all screen transitions smoothly.
    *   **CameraX (License plate scanning)**: Providing reliable camera functionality for receipt scanning.
*   **Backend**:
    *   **Firebase Authentication**: Managing user sign-up, login, and sessions.
    *   **Cloud Firestore**.
*   **AI Integration**:
    *   **Google Gemini API**: Powering the intelligent receipt scanning and data extraction.
    *   **Kotlin Serialization**: Parsing the structured JSON output from the Gemini API.

## 📱 Screenshots

* Check out how Expense Tracker looks in action:

**not yet**

## 📌 Future Improvements

*   **Monthly and Historical Bar Charts**: Enhance the analytics section with more detailed views of spending trends over time.
*   **Budgeting Features**: Allow users to set spending limits for categories and track their progress.
*   **Recurring Transactions**: Implement functionality for automatically logging regular income or expenses.
*   **Data Export**: Add options to export financial data to formats like CSV or Excel (possibly using Cloud Functions!).
*   **Store Scanned Images**: Integrate Firebase Storage to save the original scanned receipt images alongside the transaction data in Firestore.
*   **User Profile Enhancements**: Allow users to add and manage profile pictures.
*   **Advanced Filtering & Sorting**: Provide more robust options for finding and organizing transactions.
*   **Multi-currency Support**: Add the ability to handle transactions in different currencies.
*   
## 📞 Contact
For any inquiries, feel free to contact **EnNori**  at [enoridz11@gmail.com](mailto:enoridz11@gmail.com)
---

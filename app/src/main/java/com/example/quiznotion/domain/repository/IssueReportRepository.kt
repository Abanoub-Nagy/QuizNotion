package com.example.quiznotion.domain.repository

import com.example.quiznotion.domain.model.IssueReport
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result

interface IssueReportRepository {
    suspend fun insertIssueReport(report: IssueReport): Result<Unit, DataError>
}
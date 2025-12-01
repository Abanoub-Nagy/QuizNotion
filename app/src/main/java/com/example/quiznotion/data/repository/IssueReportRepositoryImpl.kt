package com.example.quiznotion.data.repository

import com.example.quiznotion.data.mapper.toIssueReportDto
import com.example.quiznotion.data.remote.RemoteQuizDataSource
import com.example.quiznotion.domain.model.IssueReport
import com.example.quiznotion.domain.repository.IssueReportRepository
import com.example.quiznotion.domain.util.DataError
import com.example.quiznotion.domain.util.Result


class IssueReportRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource
) : IssueReportRepository {

    override suspend fun insertIssueReport(
        report: IssueReport
    ): Result<Unit, DataError> {
        val reportDto = report.toIssueReportDto()
        return remoteDataSource.insertIssueReport(reportDto)
    }

}
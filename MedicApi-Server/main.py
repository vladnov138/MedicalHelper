import checker_client
import random
import config
import sys
import json
import uvicorn
import os
from fastapi import FastAPI, Request

# Init section
app = FastAPI()
username = config.username
password = config.password
authUrl = config.priaid_authservice_url
healthUrl = config.priaid_healthservice_url
language = config.language
api_medic_client = checker_client.DiagnosisClient(username=username, password=password, authServiceUrl=authUrl,
                                                  healthServiceUrl=healthUrl, language=language)


@app.get("/symptoms")
async def get_symptoms():
    return {"symptoms": api_medic_client.loadSymptoms()}


@app.get("/issues")
async def get_issues():
    return {"issues": api_medic_client.loadIssues()}


@app.post("/issue_info")
async def get_issue_info(info: Request):
    req_info = await info.json()
    issue_id = req_info["issue_id"]
    return {"information": api_medic_client.loadIssueInfo(issue_id)}


@app.post("/identify_disease")
async def identify_disease(info: Request):
    req_info = await info.json()
    potential_diseases = req_info["symptoms"]
    gender = req_info["gender"]
    year_of_birth = req_info["year_of_birth"]
    return {"result": api_medic_client.loadDiagnosis(potential_diseases, 'Male', year_of_birth)}


def main():
    uvicorn.run(f"{os.path.basename(__file__)[:-3]}:app", log_level="info")


if __name__ == '__main__':
    main()

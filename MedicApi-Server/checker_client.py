import requests
import hmac, hashlib
import base64
import json
from enum import Enum

Gender = Enum('Gender', 'Male Female')

SelectorStatus = Enum('SelectorStatus', 'Man Woman Boy Girl')


class DiagnosisClient:
    def __init__(self, username, password, authServiceUrl, language, healthServiceUrl):
        self._handleRequiredArguments(username, password, authServiceUrl, healthServiceUrl, language)

        self._language = language
        self._healthServiceUrl = healthServiceUrl
        self._token = self._loadToken(username, password, authServiceUrl)

    @staticmethod
    def _loadToken(username, password, url):

        rawHashString = hmac.new(bytes(password, encoding='utf-8'), url.encode('utf-8'), digestmod=hashlib.md5).digest()
        computedHashString = base64.b64encode(rawHashString).decode()

        bearer_credentials = username + ':' + computedHashString
        postHeaders = {
            'Authorization': 'Bearer {}'.format(bearer_credentials)
        }
        responsePost = requests.post(url, headers=postHeaders)

        data = json.loads(responsePost.text)
        return data

    @staticmethod
    def _handleRequiredArguments(username, password, authUrl, healthUrl, language):
        if not username:
            raise ValueError("Argument missing: username")

        if not password:
            raise ValueError("Argument missing: password")

        if not authUrl:
            raise ValueError("Argument missing: authServiceUrl")

        if not healthUrl:
            raise ValueError("Argument missing: healthServiceUrl")

        if not language:
            raise ValueError("Argument missing: language")

    def _loadFromWebService(self, action):
        extraArgs = "token=" + self._token["Token"] + "&format=json&language=" + self._language
        if "?" not in action:
            action += "?" + extraArgs
        else:
            action += "&" + extraArgs

        url = self._healthServiceUrl + "/" + action
        response = requests.get(url)

        try:
            response.raise_for_status()
        except requests.exceptions.HTTPError as e:
            print("----------------------------------")
            print("HTTPError: " + e.response.text)
            print("----------------------------------")
            raise

        try:
            dataJson = response.json()
        except ValueError:
            raise requests.exceptions.RequestException(response=response)

        data = json.loads(response.text)
        return data

    def loadSymptoms(self):
        return self._loadFromWebService("symptoms")

    def loadIssues(self):
        return self._loadFromWebService("issues")

    def loadIssueInfo(self, issueId):
        if isinstance(issueId, int):
            issueId = str(issueId)
        action = "issues/{0}/info".format(issueId)
        return self._loadFromWebService(action)

    def loadDiagnosis(self, selectedSymptoms, gender, yearOfBirth):
        if not selectedSymptoms:
            raise ValueError("selectedSymptoms can not be empty")

        serializedSymptoms = json.dumps(selectedSymptoms)
        action = "diagnosis?symptoms={0}&gender={1}&year_of_birth={2}".format(serializedSymptoms, 'Male',
                                                                              yearOfBirth)
        return self._loadFromWebService(action)

    def loadSpecialisations(self, selectedSymptoms, gender, yearOfBirth):
        if not selectedSymptoms:
            raise ValueError("selectedSymptoms can not be empty")

        serializedSymptoms = json.dumps(selectedSymptoms)
        action = "diagnosis/specialisations?symptoms={0}&gender={1}&year_of_birth={2}".format(serializedSymptoms,
                                                                                              gender.name, yearOfBirth)
        return self._loadFromWebService(action)

    def loadBodyLocations(self):
        return self._loadFromWebService("body/locations")

    def loadBodySubLocations(self, bodyLocationId):
        action = "body/locations/{0}".format(bodyLocationId)
        return self._loadFromWebService(action)

    def loadSublocationSymptoms(self, locationId, selectedSelectorStatus):
        action = "symptoms/{0}/{1}".format(locationId, selectedSelectorStatus.name)
        return self._loadFromWebService(action)

    def loadProposedSymptoms(self, selectedSymptoms, gender, yearOfBirth):
        if not selectedSymptoms:
            raise ValueError("selectedSymptoms can not be empty")
        serializedSymptoms = json.dumps(selectedSymptoms)
        action = "symptoms/proposed?symptoms={0}&gender={1}&year_of_birth={2}".format(serializedSymptoms, gender.name,
                                                                                      yearOfBirth)
        return self._loadFromWebService(action)

    def loadRedFlag(self, symptomId):
        action = "redflag?symptomId={0}".format(symptomId)
        return self._loadFromWebService(action)

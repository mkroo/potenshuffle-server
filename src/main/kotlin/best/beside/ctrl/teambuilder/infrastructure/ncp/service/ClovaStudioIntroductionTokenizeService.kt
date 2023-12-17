package best.beside.ctrl.teambuilder.infrastructure.ncp.service

import best.beside.ctrl.teambuilder.application.IntroductionTokenizeService
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.ClovaStudioClient
import best.beside.ctrl.teambuilder.infrastructure.ncp.config.NaverCloudPlatformProperties
import best.beside.ctrl.teambuilder.infrastructure.ncp.request.ChatCompletionRequest
import org.springframework.stereotype.Service

@Service
class ClovaStudioIntroductionTokenizeService(
    private val clovaStudioClient: ClovaStudioClient,
    properties: NaverCloudPlatformProperties,
) : IntroductionTokenizeService {
    private val modelName = properties.chatCompletion.modelName
    private val apiKey = properties.chatCompletion.apiKey
    private val systemMessage = ChatCompletionRequest.SystemMessage(
        """
            - 자기소개에서 핵심 키워드를 추출합니다.
            - 키워드는 콤마로만 구분해서 출력합니다.
            - 키워드 수식어들을 출력하지 않습니다
            
            ### 질문
            안녕하세요! 312 포텐데이에 벡엔드 개발자로써 참여하는 신문기입니다 :미소짓는_얼굴:
            아래의 소개를 보시고 같이 진행하고 싶으신 팀이 있다면 언제든지 DM 부탁드립니다!
            :어지러운: About me
            1년의 창업경험이 있습니다! 아직도 끓어오르는 창업 의지로 치열하게 고민하고 사용자에게 직접 닿을 수 있는 프로젝트를 진행해보고 싶습니다
            4년의 백엔드개발을 바탕으로 팀원분들의 상상을 현실로 만들어 보겠습니다!
            첫 창업부터 현재의 회사까지 다른 직군의 팀원분들께 커뮤니케이션 스킬이 좋다는 피드백을 지속적으로 듣고 있습니다 ㅎ
            아쉽게도 생성 AI 관련 프로덕트를 경험해본적은 없지만 개발 스택처럼 처음 접하는 분야도 빠르게 follow up이 가능합니다 :미소짓는_얼굴:
            :안내하는_남성: 이런 분들과 함께 하고 싶어요!
            한계를 정하지 않고 유저가 만족할 수 있다면 어떠한 방법으로든 고민할 수 있는 열정적인 분들과 함께하고 싶습니다 :불:
            프로젝트를 진행하면서 재미있고 책임감있는 분들과 함께하고 싶습니다 :반짝임:
            :끝이_말린_페이지: 업무 경험
            게임 큐레이션 서비스를 창업하여 약 1년간 운영
            기획/개발/운영 모든 단계를 경험
            임베딩 벡터 기반의 게임 추천 시스템 개발
            레스토랑 SasS 기업에서 백엔드 개발자로 2년 6개월 근무
            실시간 확정 예약 시스템 설계 및 개발
            다양한 PG사를 추상화하여 간단하게 사용할 수 있는 결제 시스템 개발 (계약 특성 상 iamport를 이용할 수 없었습니다 :울다: )
            카카오 오픈 빌더를 이용한 챗봇 서버 개발
            AWS 기반의 인프라 재설계 및 반영
            (현재) 채용 관련 기업에서 백엔드 개발자로 1년 6개월 근무 중
            인재 검색 및 채용 관련 도메인을 경험
            :별: 개발 스택
            이번 312 포텐데이에서 사용 예정인 기술 스택
            Spring + Kotlin
            데이터베이스는 기획 요구사항에 따라 MySQL, MongoDB, Elasticsearch 중 적절한것을 사용할 예정입니다
            과거에 사용하였으나 현재는 주력으로 사용하지 않는 기술 스택
            Node.js + Typescript
            Spring + Kotlin
            Mongodb
            현재 주력으로 사용중인 기술 스택
            Rails + Ruby
            Elasticsearch
            MySQL
            
            
            ### 대답
            창업, 백엔드, 개발, 커뮤니케이션이 좋음, 열정, 책임감
        """.trimIndent()
    )

    override fun tokenize(introduction: String): List<String> {
        val userMessage = ChatCompletionRequest.UserMessage(content = introduction)

        val request = ChatCompletionRequest(
            messages = listOf(
                systemMessage,
                userMessage
            ),
            topP = 0.6,
            topK = 10,
            maxTokens = 256,
            temperature = 0.01,
            repeatPenalty = 10.0
        )

        val response = clovaStudioClient.chatCompletion(modelName, apiKey, request)
        return response.result.message.content.split(",").map(String::trim).filter { it.length > 1 }
    }
}
import * as React from 'react';

import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Calendar from 'components/common/option/Calendar';

import TeamBanner from 'components/team/TeamBanner';
import TeamMemberSearchList from 'components/common/card/TeamMemberSearchList';

import MultipleSelect from 'components/common/option/MultipleSelect';
import MultilineText from 'components/common/option/MultilineText';
import SingleSelect from 'components/common/option/SingleSelect';
import SingleSelectNumber from 'components/common/option/SingleSelectNumber';
import SingleSelectOnOff from 'components/common/option/SingleSelectOnOff';
import SingleSelectRegion from 'components/common/option/SingleSelectRegion';

// axios 입력값을 불러와서 띄우기

export default function TeamDetailPage() {
  const teamBanner = {
    title: '팀이름', // project_title GET
    leader: '팀장 이름', // GET
    image: 'https://source.unsplash.com/random', // project_image GET
  };
  return (
    <>
      <TeamBanner post={teamBanner} />
      <Container fixed>
        <h2>모집 정보</h2>
        <Box>
          <h4>모집 구분</h4>
          <SingleSelect></SingleSelect>
          <h4>모집 인원</h4>
          <SingleSelectNumber></SingleSelectNumber>
          <h4>모집 기간</h4>
          <Calendar></Calendar>
          <h4>진행 방식</h4>
          <SingleSelectOnOff></SingleSelectOnOff>
          <h4>지역</h4>
          <SingleSelectRegion></SingleSelectRegion>
          <h4>기술 스택</h4>
          <MultipleSelect></MultipleSelect>
        </Box>
      </Container>
      <Container fixed>
        <h2>팀원 소개</h2>
        <MultilineText></MultilineText>
      </Container>
      <Container fixed>
        <h2>팀원 소개</h2>
        <TeamMemberSearchList></TeamMemberSearchList>
      </Container>
    </>
  );
}

import * as React from 'react';
import { useEffect, useState } from 'react';
import customAxios from 'utils/axios';
import { useSelector, useDispatch } from 'react-redux';
import { handleUpdate } from 'redux/team';
import { handleSuccessState } from 'redux/snack';
import {
  Container,
  Paper,
  IconButton,
  Button,
  Stack,
  Grid,
  styled,
  Avatar,
} from '@mui/material/';
import CardList from 'components/common/card/CardList';
import Typography from '@mui/material/Typography';
import { useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';
import Footer from 'components/common/footer/Footer';
import useMobile from 'hooks/useMobile';
import TeamApplyOffer from 'components/team/TeamApplyOffer';
// axios 입력값을 불러와서 띄우기

export default function TeamDetailPage() {
  const isMobile = useMobile();
  //navigation
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const goToUpdate = () => {
    // 팀 수정 눌렀을 때, 이동할 프론트 주소
    navigate(`/TeamUpdatePage`);
    dispatch(handleUpdate({ projectId: projectId }));
  };

  // const [isLoaded, setIsLoaded] = useState(false);
  const [lead, setLead] = useState('');
  const [detail, setDetail] = useState([]);
  const [cards, setCards] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  // redux
  const projectId = useSelector(state => state.team.projectId);

  // axios
  useEffect(() => {
    customAxios.authAxios
      // 해당 id의 프로젝트 조회됨 axios 주소
      .get(`/projects/detail?projectId=${projectId}`)
      .then(response => {
        setDetail(response.data);
        console.log(response.data);
        console.log('조회성공!');
        setCards(response.data.profileResultDtoList);
        console.log(response.data.profileResultDtoList);
        setLead(response.data.leader);
        console.log(response.data.leader);
      })
      .catch(error => {
        console.log(error.data);
      });
    setIsLoaded(true);
  }, [projectId, isLoaded]);

  const handleApply = () => {
    customAxios
      .authAxios({
        method: 'POST',
        url: '/apply',
        data: { projectId: projectId },
      })
      .then(response => {
        dispatch(
          handleSuccessState({
            open: true,
            message: '지원이 완료 되었습니다.',
            severity: 'success',
          }),
        );
        console.log(response.data);
        console.log('지원완료!');
      })
      .catch(error => {
        console.log(error);
      });
  };

  return (
    <>
      <Container fixed>
        {/* <TeamBanner post={teamBanner} /> */}
        {/* 팀 이미지 */}
        <Paper
          sx={{
            position: 'relative',
            color: '#fff',
            mb: 4,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            backgroundPosition: 'center',
            backgroundImage: `url(${detail.img})`,
            height: 'calc(400px + 10vw)', // 반응형 웹 calc
            maxHeight: 'calc(100vh - 56px)',
          }}
        >
          <Dim />
          <Grid container>
            <Grid item md={6}>
              <Box
                sx={{
                  position: 'relative',
                  p: { xs: 3, md: 6 },
                  pr: { md: 0 },
                }}
              >
                <Typography
                  component="h1"
                  variant="h2"
                  color="inherit"
                  gutterBottom
                >
                  {/* 팀 이름  */}
                  {/* <SingleTextField ref={titleRef}></SingleTextField> */}
                  {detail.title}
                </Typography>
                <Typography variant="h4" color="inherit" paragraph>
                  {/* 팀장 이름 */}
                  {detail.leaderNickname}
                </Typography>
              </Box>
            </Grid>
          </Grid>
        </Paper>
        <Grid container justifyContent="flex-end">
          <Stack
            direction="row"
            spacing={2}
            // justifyContent="flex-end"
            sx={{ pt: 4 }}
          >
            {/* leader 값이 true일 경우 제안 및 지원 확인, false일 경우 지원 보내기 */}
            {lead ? (
              <IconButton size="small" variant="contained" color="primary">
                {/* 지원 및 제안 버튼 return 해주는 component */}
                <TeamApplyOffer isMobile={isMobile}></TeamApplyOffer>
              </IconButton>
            ) : (
              <Button
                size="small"
                variant="contained"
                color="primary"
                onClick={handleApply}
              >
                지원 보내기
              </Button>
            )}

            {/* leader 값이 true이면 팀 수정 보이고, false이면 안 보임 */}
            {lead ? (
              <Button
                onClick={goToUpdate}
                size="small"
                variant="contained"
                color="primary"
              >
                팀 수정
              </Button>
            ) : (
              ''
            )}

            {/* leader 값이 true이면 팀 삭제 보이고, false이면 안 보임 */}
            {lead ? (
              <Button
                size="small"
                variant="contained"
                color="primary"
                onClick={async () => {
                  console.log(projectId); // 잘 뜸
                  await customAxios.authAxios
                    .delete('/projects', {
                      data: {
                        projectId: projectId,
                      },
                    })
                    .then(e => {
                      console.log(e);
                      console.log('삭제완료!');
                      alert('게시물이 삭제되었습니다.');
                      navigate('/'); // 삭제 후 홈으로 보내기 (프론트 주소)
                    });
                }} // 팀 삭제 버튼 클릭 시, 삭제 요청보내기
              >
                팀 삭제
              </Button>
            ) : (
              ''
            )}
          </Stack>
        </Grid>
      </Container>
      <Container fixed>
        <h2>모집 정보</h2>
        <Paper
          variant="outlined"
          elevation={0}
          style={{
            padding: 20,
          }}
        >
          <h4>모집 구분</h4>
          <Typography variant="body1" color="initial">
            {detail.category}
          </Typography>
          <h4>모집 정원</h4>
          <Typography variant="body1" color="initial">
            {detail.totalPeople}
          </Typography>
          <h4>마감 날짜</h4>
          <Typography variant="body1" color="initial">
            {detail.endDate}
          </Typography>
          <h4>진행 방식</h4>
          <Typography variant="body1" color="initial">
            {detail.projectStatus}
          </Typography>
          <h4>지역</h4>
          <Typography variant="body1" color="initial">
            {detail.areaId}
          </Typography>
          <h4>기술 스택</h4>
          <Typography component="div" variant="body1" color="initial">
            {detail.projectTechStacks &&
              detail.projectTechStacks.map(tech => (
                <Stack
                  key={tech.name}
                  direction="row"
                  sx={{
                    display: 'inline-flex',
                    justifyContent: 'space-between',
                  }}
                >
                  <span>{tech.name}</span>
                  <MoaImg src={tech.logo} />
                </Stack>
              ))}
          </Typography>
        </Paper>
      </Container>
      <Container fixed>
        <h2>팀 소개</h2>
        <Paper
          variant="outlined"
          elevation={0}
          style={{
            padding: 20,
          }}
        >
          <Typography variant="body1" color="initial">
            {detail.contents}
          </Typography>
        </Paper>
      </Container>
      <Container fixed>
        <h2>팀원 소개</h2>
        <CardList cards={cards} type="member"></CardList>
      </Container>
      <Footer></Footer>
    </>
  );
}

const MoaImg = styled(Avatar)`
  min-width: 40px;
  min-height: 40px;
  box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.25);
`;

const Dim = styled(Box)`
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.2);
`;

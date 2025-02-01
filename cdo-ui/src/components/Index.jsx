import React from 'react';
import {Link} from 'react-router-dom';

const Index = () => {

    return (
        <div>

            <div className="text-center" style={{marginTop: '-170px'}}>
                <h1 className="mb-5"
                    style={{
                        fontWeight: 'bold',
                        fontSize: '3.5rem',
                        color: 'white'
                    }}>ЦЕНТР ДИСТАНЦИОННОГО ОБУЧЕНИЯ МГУ</h1>
            </div>

            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '15vh',
                flexWrap: 'wrap',
                gap: '15px'
            }}>

                <div className="card p-4 shadow"
                     style={{
                         width: '350px',
                         height: '111px',
                         backgroundColor: 'rgba(255, 255, 255, 0.5)',
                         border: '1px solid rgba(0, 0, 0, 0.1)',
                         marginBottom: '15px',
                     }}>
                    <div className="d-flex align-items-center mb-3">
                        <img src="/images/logocdo.svg" alt="Logo" style={{width: '65px'}}/>
                        <div className="col ps-3">
                            <Link to="/index-video-conf"
                                  className="btn btn-link d-block"
                                  style={{
                                      fontWeight: 'bold',
                                      textDecoration: 'none',
                                      position: 'relative',
                                      display: 'inline-block',
                                      fontSize: '18px',
                                      padding: '0px',
                                      textAlign: 'left',
                                      width: 'max-content',
                                      borderRadius: 0
                                  }}
                                  onMouseEnter={(e) => {
                                      e.target.style.borderBottom = '2px solid #000';
                                  }}
                                  onMouseLeave={(e) => {
                                      e.target.style.borderBottom = 'none';
                                  }}>
                                ВИДЕОКОНФЕРЕНЦИИ
                            </Link>
                            <div className="box-content">
                                <p className="mb-0">Ссылки на преподавателей</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '10vh',
                flexWrap: 'wrap',
                gap: '15px'
            }}>
                <div className="card p-4 shadow"
                     style={{
                         width: '350px',
                         height: '111px',
                         backgroundColor: 'rgba(255, 255, 255, 0.5)',
                         border: '1px solid rgba(0, 0, 0, 0.1)',
                         marginBottom: '20px',
                     }}>
                    <div className="d-flex align-items-center mb-3">
                        <img src="/images/logocdo.svg" alt="Logo" style={{width: '65px'}}/>
                        <div className="col ps-3">
                            <Link to="/schedule"
                                  className="btn btn-link d-block"
                                  style={{
                                      fontWeight: 'bold',
                                      textDecoration: 'none',
                                      position: 'relative',
                                      display: 'inline-block',
                                      fontSize: '18px',
                                      padding: '0px',
                                      textAlign: 'left',
                                      width: 'max-content',
                                      borderRadius: 0
                                  }}
                                  onMouseEnter={(e) => {
                                      e.target.style.borderBottom = '2px solid #000';
                                  }}
                                  onMouseLeave={(e) => {
                                      e.target.style.borderBottom = 'none';
                                  }}>
                                РАСПИСАНИЕ
                            </Link>
                            <div className="box-content">
                                <p className="mb-0">Расписание онлайн</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Index;
